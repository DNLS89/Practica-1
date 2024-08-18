
package Tramite;

import SQL.SQL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public class MovimientoBE extends Tramite{
    private SQL sql;
    private Connection connection;
    private int indiceTipoMovimiento;
    private String fechaMOv;
    private String numeroTarjeta;
    private String establecimientoMov;
    private String descripcionMov;
    private BigDecimal monto;
    private BigDecimal saldoTarjeta;
    private BigDecimal limiteTarjeta;
    

    public MovimientoBE(SQL sql, int indiceTipoMovimiento, String fechaMov, String numeroTarjeta, String establecimientoMov, 
            BigDecimal monto, String descripcionMov) {
        this.sql = sql;
        this.connection = sql.getConnection();
        this.indiceTipoMovimiento = indiceTipoMovimiento;
        this.fechaMOv = fechaMov;
        this.numeroTarjeta = numeroTarjeta;
        this.establecimientoMov = establecimientoMov;
        this.descripcionMov = descripcionMov;
        this.monto = monto;
    }
    
        @Override
    public void procesarTramite() {
        if (comprobarCumpleExistenDatos()) {
            extraerSaldo();
            
            if (indiceTipoMovimiento == 0) {
                //CARGO  GASTAR
                if (saldoDisponible()) {
                    cargoTarjeta();
                }
            } else {
                //ABONO   AGREGAR
                abonarTarjeta();
            }
            JOptionPane.showMessageDialog(null, "Movimiento Realizado Exitosamente", "Proceso Realizado", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public boolean saldoDisponible() {
        //Abajo el limite es según el tipo de tarjeta
        //Abajo en el if el -1 significa menor y el 0 igual al valor
        if ((saldoTarjeta.add(monto)).compareTo(limiteTarjeta) == -1 || (saldoTarjeta.add(monto)).compareTo(limiteTarjeta) == 0) {
            System.out.println("Saldo tarjeta: " + saldoTarjeta + " Monto: " + monto + "Sumatoria: " + saldoTarjeta.add(monto));
            System.out.println("LImite tarjeta: " + limiteTarjeta);
            //Hay suficiente dinero para gastar
            return true;
        } else {
            //No hay dinero para gastar
            JOptionPane.showMessageDialog(null, "No hay suficiente saldo disponible para el monto seleccionado, saldo disponible \""+ saldoTarjeta 
                    + "\" ", "Saldo Insuficiente", JOptionPane.PLAIN_MESSAGE);
        }
        
        return false;
    }
    
    public void cargoTarjeta() {
        String comandoCargo = "UPDATE tarjeta set saldo = " + "\"" + saldoTarjeta.add(monto) + "\""
                    + " WHERE numero_tarjeta = " + "\"" + numeroTarjeta + "\"" + " ";
        try {
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(comandoCargo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void abonarTarjeta() {
        String comandoAbono = "UPDATE tarjeta set saldo = " + "\"" + saldoTarjeta.subtract(monto) + "\""
                    + " WHERE numero_tarjeta = " + "\"" + numeroTarjeta + "\"" + " ";
        try {
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(comandoAbono);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void extraerSaldo() {
        //No puede superar el limite, si lo supera no se procesa el 
        //Extraer el valor que haya en saldo
        String selectTarjetaMovimiento = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
        
        try {
            //Extrae el valor del saldo
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaMovimiento);
            if (resultSet.next()) {
                saldoTarjeta = resultSet.getBigDecimal("saldo");
                limiteTarjeta = new BigDecimal(resultSet.getInt("limite"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean comprobarCumpleExistenDatos() {
        String selectTarjetaMovimiento = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
        String estadoTarjetaMovimiento = "";

        
        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaMovimiento);
            //Existe el numero de tarjeta
            if (resultSet.next()) {
                //Existe el número de tarjeta
                
                //Comprobar estado
                estadoTarjetaMovimiento = resultSet.getString("estado");
                
                if (estadoTarjetaMovimiento.equals("ACTIVA")) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "La tarjeta ya está cancelada", "", JOptionPane.PLAIN_MESSAGE);
                    return false;
                }
                
            }  else {
                //No existe el número de tarjeta
                JOptionPane.showMessageDialog(null, "El número de tarjeta no existe", "", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
            
            //También tiene que ver que no supere el limite
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
}
