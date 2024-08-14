
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQL {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/CONTROL_CURSOS";
    //Ahora especificamos el usuario y la contrase!na que usa MYSQL
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    
    //Ahora creamos un objeto que represente a la conexion
    private Connection connection;

    public SQL() {
        
        //Intentar crear un Schema
        
        //Crear la base de datos si es que no existe
        if (existeDB() == false) {
            
        }
        
        
        //Establecer la conexion
        //establecerConexion();
        
    }
    
    public boolean existeDB() {
        
        return false;
        
        //return true;
        
    }
    
    public void establecerConexion() {
        try {
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            System.out.println("Esquemas: " + connection.getSchema());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la DB");
            e.printStackTrace();
        }
    }
    
}
