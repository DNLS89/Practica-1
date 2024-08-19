
package GestorTarjetasBE;

import SQL.SQL;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class MovimientoBE extends Tramite{
    private SQL sql;
    private Connection connection;
    private int indiceTipoMovimiento;
    private String fechaMov;
    private java.sql.Date fechaFormatoSQL;
    private String numeroTarjeta;
    private String establecimientoMov;
    private String descripcionMov;
    private BigDecimal monto;
    private BigDecimal saldoTarjeta;
    private BigDecimal limiteTarjeta;
    private String tipoMovmiento;
    private String tipoTarjeta;
    private BigDecimal interes;
    private final BigDecimal interesNacional = new BigDecimal("0.012");
    private final BigDecimal interesRegional = new BigDecimal("0.023");
    private final BigDecimal interesInternacional = new BigDecimal("0.0375");
    

    public MovimientoBE(SQL sql, int indiceTipoMovimiento, String fechaMov, String numeroTarjeta, String establecimientoMov, 
            BigDecimal monto, String descripcionMov) {
        this.sql = sql;
        this.connection = sql.getConnection();
        this.indiceTipoMovimiento = indiceTipoMovimiento;
        this.fechaMov = fechaMov;
        this.numeroTarjeta = numeroTarjeta;
        this.establecimientoMov = establecimientoMov;
        this.descripcionMov = descripcionMov;
        this.monto = monto;
    }
    
        @Override
    public void procesarTramite() {
        if (comprobarCumpleExistenDatos() && formatoFechaAdecuado()) {
            extraerSaldo();
            if (indiceTipoMovimiento == 0) {
                //CARGO  GASTAR
                tipoMovmiento = "CARGO";
                if (saldoDisponible()) {
                    cargoTarjeta();
                }
            } else {
                //ABONO   AGREGAR
                tipoMovmiento = "ABONO";
                abonarTarjeta();
            }
            
            try {
                procesarInteres();
                crearRelacionMovimiento();
            } catch (ParseException ex) {
                System.out.println("Error creando relacion");
                Logger.getLogger(MovimientoBE.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Movimiento Realizado Exitosamente", "Proceso Realizado", JOptionPane.PLAIN_MESSAGE);
        }
    }
    public void procesarInteres() {
        //No puede superar el limite, si lo supera no se procesa el 
        //Extraer el valor que haya en saldo
        String selectTarjetaMovimiento = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
        
        try {
            //Extrae el valor del saldo
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaMovimiento);
            if (resultSet.next()) {
                tipoTarjeta = resultSet.getString("tipo");
                
                if (tipoTarjeta.equals("NACIONAL")) {
                    interes = saldoTarjeta.add(monto).multiply(interesNacional);
                } else if (tipoTarjeta.equals("REGIONAL")) {
                    interes = saldoTarjeta.add(monto).multiply(interesRegional);
                } else if (tipoTarjeta.equals("INTERNACIONAL")) {
                    interes = saldoTarjeta.add(monto).multiply(interesInternacional);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean formatoFechaAdecuado() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date;
        
        try {
            this.fechaMov = fechaMov.replace("/", "-");
            date = dateFormat.parse(fechaMov);
            fechaFormatoSQL = new java.sql.Date(date.getTime());
            
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "El formato de la fecha no es el adecuado", "Formato Fecha Incorrecto", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
    }
    
    public void crearRelacionMovimiento() throws ParseException {
        //Estraer datos usuario según el número de tarjeta, para esto extraer el numeor de solocitud
        int idUsuario = 0;
        int numeroSolicitud = 0;
        String selectTarjetaMovimiento = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
        
        try {
            //Estraer datos usuario según el número de tarjeta, para esto extraer el numeor de solocitud
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaMovimiento);
            if (resultSet.next()) {
                idUsuario = resultSet.getInt("numero_solicitud");
                numeroSolicitud = idUsuario; 
            }
           //Ingresa los datos a la tabla de RELACION MOVIMIENTO
           String comandoCrearRelacion = "INSERT INTO movimiento (id_usuario, numero_solicitud, fecha_movimiento,"
                   + " descripcion_movimiento, establecimiento_movimiento, monto, tipoMov, interes) " + "VALUE ('" + idUsuario + "', '"
                   + "" + numeroSolicitud + "','" + fechaFormatoSQL + "','" + descripcionMov + "','" + establecimientoMov + "', '"
                   + "" + monto + "', '" + tipoMovmiento + "', '" + interes + "');";
            statementInsert = connection.createStatement();
            statementInsert.executeUpdate(comandoCrearRelacion);
        } catch (Exception e) {
            System.out.println("Error al ingresar datos de la relacion");
            e.printStackTrace();
        }
    }
    
    public boolean saldoDisponible() {
        //Abajo el limite es según el tipo de tarjeta
        //Abajo en el if el -1 significa menor y el 0 igual al valor
        if ((saldoTarjeta.add(monto)).compareTo(limiteTarjeta) == -1 || (saldoTarjeta.add(monto)).compareTo(limiteTarjeta) == 0) {
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
