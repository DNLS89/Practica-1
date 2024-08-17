
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.out.println("Error PRUEBA al conectar a la DB");
            e.printStackTrace();
        }
        try {
            Statement statementInsert = connection.createStatement();
            //Abajo lo de la derecha regresa la cantidad de rows modificadas al igual que en la terminal por si es que nos interesa
            System.out.println("previo");
            int exists = statementInsert.executeUpdate("SELECT EXISTS(SELECT 1 FROM yourTableName)");
            System.out.println("PRUEBA AHORA: " + exists);
        } catch (Exception e) {
            System.out.println("ERROR PRUEBA");
        }
    }
    
    public void leer_EscribirDB(){
        //Acá podría ser un get() de connection para ingresar los datos, quedaría mejor tener una instrucción por cada trámite
        
    }
    
    public void escribirDatosSolicitudSQL(String comandoUsuario, String comandoTarjeta) {
        
        try {
            Statement statementInsert = connection.createStatement();
            //Abajo lo de la derecha regresa la cantidad de rows modificadas al igual que en la terminal por si es que nos interesa
            int rowsAffected = statementInsert.executeUpdate(comandoUsuario);
            System.out.println("Rows affected " + rowsAffected);
            
            rowsAffected = statementInsert.executeUpdate(comandoTarjeta);
            System.out.println("Rows affected " + rowsAffected);
            //Abajo notar que solo se puede recolectar SQLException
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
    }

    public Connection getConnection() {
        return connection;
    }
}
