
package main;

import FrontEnd.Pantalla;
import GestorArchivo.GestorArchivo;
import SQL.SQL;


public class Practica1 {

    public static void main(String[] args) {
        
        SQL sql = new SQL();
        
        
        Pantalla panta = new Pantalla(sql);
        panta.setVisible(true);
        panta.setLocationRelativeTo(null);
        
        
        
    }
}
