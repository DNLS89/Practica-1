
package SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQL {
    private static final String URL_MYSQL = "jdbc:mysql://localhost:3306/";
    //Ahora especificamos el usuario y la contrase!na que usa MYSQL
    private static final String USER = "root";
    private static final String PASSWORD = "123";
    
    //Ahora creamos un objeto que represente a la conexion
    private Connection connection;
    
    public void establecerConexion() {
        try {
            connection = DriverManager.getConnection(URL_MYSQL, USER, PASSWORD);
            
            String insert = "create schema PRUEBA";
            Statement statementInsert = connection.createStatement();
            //Abajo lo de la derecha regresa la cantidad de rows modificadas al igual que en la terminal por si es que nos interesa
            statementInsert.executeUpdate(insert);
            
        } catch (SQLException e) {
            System.out.println("Error PRUEBA al conectar a la DB");
            e.printStackTrace();
        }
    }

    public SQL() {
        
        //Intentar crear un Schema
        
        //Crear la base de datos si es que no existe
        
        //Establecer la conexion
        establecerConexion();
        
        
    }
    
    public boolean existeDB() {
        return false;
        //return true;
    }
    
    
}
