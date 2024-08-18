package Tramite;

import GestorTarjetasFE.Cancelacion;
import SQL.SQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CancelacionBE extends Tramite{
    private String numeroTarjetaCancelar;
    private SQL sql;
    private Connection connection;
    private Cancelacion cancelacionFE;
    private String nombreUsuario = "";
    private String direccionUsuario = "";
    
    //Constructor para la GUI
    public CancelacionBE(String numeroTarjetaCancelar, SQL sql, Cancelacion cancelacionFE) {
        this.numeroTarjetaCancelar = numeroTarjetaCancelar;
        this.sql = sql;
        this.connection = sql.getConnection();
        this.cancelacionFE = cancelacionFE;
    }

    //Constructor para el archivo
    public CancelacionBE(String numeroTarjetaCancelar, SQL sql) {
        this.numeroTarjetaCancelar = numeroTarjetaCancelar;
        this.sql = sql;
        this.connection = sql.getConnection();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }
    
    public void mostrarDatosInterfaz() {
        //Solo muestra los datos del usuario al que pertenece
        
        //Extraer el numero de solicitud y asociarlo con el del usuario y de esa manera extraer los datos del usuario
        String selectTarjetaCancelar = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjetaCancelar+ "\"";
        int numeroSolicitudTarjeta = 0;
        
        if (comprobarCumpleEstado() && comprobarCumpleSaldoPendiente()) {
            try {
                //Extrae el numero_solicitud de la tarjeta por cancelar
                Statement statementInsert = connection.createStatement();
                ResultSet resultSet = statementInsert.executeQuery(selectTarjetaCancelar);
                resultSet.next();
                numeroSolicitudTarjeta = resultSet.getInt("numero_solicitud");

                //Ahora extraer nombre y direccion de la persona dueña de la tarjeta
                String comandoDatosUsuarios = "SELECT * FROM usuario WHERE id_usuario like \"" + numeroSolicitudTarjeta + "\"";
                resultSet = statementInsert.executeQuery(comandoDatosUsuarios);
                resultSet.next();
                String nombreUsuario = resultSet.getString("nombre");
                String direccionUsuario = resultSet.getString("direccion");
                //Ingresa los datos al FE
                cancelacionFE.getTxtDireccion().setText("Dirección: " + direccionUsuario);
                cancelacionFE.getTxtNombreUsuario().setText("Nombre: " + nombreUsuario);
                
                //Escoger 
                int decision = JOptionPane.showConfirmDialog(null, ("Seguro que desea eliminar la tarjeta de "
                        + nombreUsuario), "Desea Continuar?", JOptionPane.YES_NO_OPTION);

                if (decision == 0) {
                    procesarTramite();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void procesarTramite() {
        
        if (comprobarCumpleEstado() && comprobarCumpleSaldoPendiente()) {
            cambiarEstadoTarjeta();
            try {
                agregarFechaCancelacion();
            } catch (ParseException ex) {
                Logger.getLogger(CancelacionBE.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "Cancelación Procesada", "", JOptionPane.PLAIN_MESSAGE);
        }
        
    }
    
    public boolean comprobarCumpleSaldoPendiente() {
        String selectTarjetaCancelar = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjetaCancelar+ "\"";
        int saldoPendiente = 0;
        
        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaCancelar);
            //Extrae cantidad saldo pendiente
            resultSet.next();
            saldoPendiente = resultSet.getInt("saldo_pendiente");
            
            if (saldoPendiente == 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "La tarjeta no puede cancelarse hasta que pague el saldo pendiente", "", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void cambiarEstadoTarjeta() {
        try {
            String cambioEstadoTarjeta = "UPDATE tarjeta set estado = \"CANCELADA\" where numero_tarjeta = \"" + numeroTarjetaCancelar + "\"";
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(cambioEstadoTarjeta);
        } catch (SQLException e) {
            System.out.println("Error cancelando tarjeta");
            e.printStackTrace();
        }
    }
    
    public void agregarFechaCancelacion() throws ParseException {
        Date thisDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(thisDate.getTime());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //this.fechaCreacion = dateFormat.format(thisDate);
        //Date date = dateFormat.parse(fechaCreacion);
        //Los ingresa a la base de datos
        try {
            String comandoIngresarFechaCreacion = "UPDATE tarjeta set fecha_cancelacion = " + "\"" + sqlDate + "\""
                    + " WHERE numero_tarjeta = " + "\"" + numeroTarjetaCancelar + "\"" + " ";
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(comandoIngresarFechaCreacion);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean comprobarCumpleEstado() {
        String selectTarjetaCancelar = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjetaCancelar+ "\"";
        String estadoTarjetaCancelar = "";
        
        //Si el numero de tarjeta no aparece entonces no ha sido solicitada luego comprueba el estado (DEBE SER ACTIVA)
        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectTarjetaCancelar);
            //Existe el numero de tarjeta
            if (resultSet.next()) {
                //Existe el número de tarjeta entonces extraigo el estado y luego comprueba estado sea ACTIVA
                estadoTarjetaCancelar = resultSet.getString("estado");
                
                if (estadoTarjetaCancelar.equals("ACTIVA")) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "La tarjeta ya está cancelada", "", JOptionPane.PLAIN_MESSAGE);
                    return false;
                }
                
            } else {
                //No existe el número de tarjeta
                JOptionPane.showMessageDialog(null, "El número de tarjeta no existe", "", JOptionPane.PLAIN_MESSAGE);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;        
    }
    
}
