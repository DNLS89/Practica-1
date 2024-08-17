/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tramite;

/**
 *
 * @author debian
 */
public class AutorizacionBE extends Tramite{

    public AutorizacionBE() {
        
        
        
    }
    
    public void comprobarCumpleRequisitos() {
        
    }

    
    
    
    @Override
    public void procesarTramite() {
        super.procesarTramite(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    
    
    public String nuevoNumeroTarjeta(String numeroTarjetaAnterior) {
        String[] divNumeroTarjeta = numeroTarjetaAnterior.split("");
        //Abajo mete el número final es uno solo
        String numeroNuevo = divNumeroTarjeta[13] + divNumeroTarjeta[15] + divNumeroTarjeta[16] + divNumeroTarjeta[17]
                + divNumeroTarjeta[18];
        
        int numeroNuevoInt = Integer.parseInt(numeroNuevo);
        numeroNuevoInt++;
        
        String numeroNuevoString = String.valueOf(numeroNuevoInt);
        
        //Abajo agrega ceros que son eliminados al pasarlo a int
        if (numeroNuevoString.length() < 5) {
            int cerosPorAgregar = 5 - numeroNuevoString.length();
            for (int cerosAgregados = 0; cerosAgregados < cerosPorAgregar; cerosAgregados++) {
                numeroNuevoString = "0" + numeroNuevoString;
            }
        }
        
        String[] divNumeroNUevo = numeroNuevoString.split("");
        //Abajo mete el número nuevo de tarjeta 
        divNumeroTarjeta[13]= divNumeroNUevo[0];
        divNumeroTarjeta[15]= divNumeroNUevo[1];
        divNumeroTarjeta[16]= divNumeroNUevo[2];
        divNumeroTarjeta[17]= divNumeroNUevo[3];
        divNumeroTarjeta[18]= divNumeroNUevo[4];
        String nuevoNumeroTarjeta = "";
        for (String numero : divNumeroTarjeta) {
            nuevoNumeroTarjeta += numero;
        }
        
        return nuevoNumeroTarjeta;
    }
    
}
