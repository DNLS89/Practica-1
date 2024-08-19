/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReportesBE;

import SQL.SQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;


public abstract class Reporte {
    
    public void procesar() {
        
    }
    
    
    
//    private SQL sql;
//    private Connection connection;
//    //4 datos que filtran no. tarjeta, tipo, saldos mayores a, interés mayor a
//    private String numeroTarjeta;
//
//    public Reporte(SQL sql, String numeroTarjeta) {
//        this.sql = sql;
//        this.connection = sql.getConnection();
//        this.numeroTarjeta = numeroTarjeta;
//    }
//    
//    public boolean comprobarTarjetaExiste() {
//        String comandoTarjeta = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
//        
//        try {
//            Statement statementInsert = connection.createStatement();
//            ResultSet resultSet = statementInsert.executeQuery(comandoTarjeta);
//            //Existe el numero de tarjeta
//            if (resultSet.next()) {
//                //Existe el número de tarjeta
//                return true;
//                
//            } else {
//                //No existe el número de tarjeta
//                JOptionPane.showMessageDialog(null, "El número de tarjeta no existe o el formato es erróneo", "", JOptionPane.PLAIN_MESSAGE);
//                return false;
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
    
}
