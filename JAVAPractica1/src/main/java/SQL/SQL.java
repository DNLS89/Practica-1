
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SQL {
    //private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/CONTROL_TARJETAS";
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/PRUEBA";
    //Ahora especificamos el usuario y la contrase!na que usa MYSQL
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    
    //Ahora creamos un objeto que represente a la conexion
    private Connection connection;
    
    public SQL() {
        establecerConexion();
        
        
    }
    
    public void establecerConexion() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            }
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.out.println("Error PRUEBA al conectar a la DB");
            e.printStackTrace();
        }
        
        //Tratar ver si existe una base de datos, tal vez lo mejor es ver si existen datos
//        try {
//            Statement statementInsert = connection.createStatement();
//            //Abajo lo de la derecha regresa la cantidad de rows modificadas al igual que en la terminal por si es que nos interesa
//            int exists = statementInsert.executeUpdate("SELECT EXISTS(SELECT 1 FROM yourTableName)");
//            System.out.println("PRUEBA AHORA: " + exists);
//        } catch (Exception e) {
//            System.out.println("ERROR PRUEBA");
//        }
    }
    
    public void leer_EscribirDB(){
        //Acá podría ser un get() de connection para ingresar los datos, quedaría mejor tener una instrucción por cada trámite
        
    }
    //SOLICITUD
    public String escribirDatosSolicitudSQL(String comandoUsuario, String comandoTarjeta) {
        String numeroSolicitud = "";
        try {
            Statement statementInsert = connection.createStatement();
            //Abajo lo de la derecha regresa la cantidad de rows modificadas al igual que en la terminal por si es que nos interesa
            int rowsAffected = statementInsert.executeUpdate(comandoUsuario);
            
            rowsAffected = statementInsert.executeUpdate(comandoTarjeta);
            
            //Conseguir la última ID para que se muestre el último numero de solicitud
            String numeroTarjeta = "select max(numero_solicitud) from tarjeta";
            statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(numeroTarjeta);
            if (resultSet.next()) {
                numeroSolicitud = resultSet.getString("max(numero_solicitud)");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return numeroSolicitud;
    }
    
    //AUTORIZACION
    public void leerDatosAutorizacion () {
        
        
    }
    
    
    public Connection getConnection() {
        return connection;
    }
}
