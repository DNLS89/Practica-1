
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQL {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/CONTROL_TARJETAS";
    //Ahora especificamos el usuario y la contrase!na que usa MYSQL
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    
    //Ahora creamos un objeto que represente a la conexion
    private Connection connection;
    
    public SQL() {
        //Establecer la conexion
        establecerConexion();
    }
    
    public void establecerConexion() {
        try {
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            
        } catch (SQLException e) {
            System.out.println("Error PRUEBA al conectar a la DB");
            e.printStackTrace();
        }
    }
    
    public void leer_EscribirDB(){
        //Acá podría ser un get() de connection para ingresar los datos, quedaría mejor tener una instrucción por cada trámite
        
    }

    public Connection getConnection() {
        return connection;
    }
    
    
}
