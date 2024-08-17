/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Tramite;

import GestorTarjetasFE.ENUMTipoTarjeta;
import SQL.SQL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author debian
 */
public class SolicitudBE extends Tramite{
    //USUARIO
    private String nombreUsuario;
    private int salario;
    private String direccion;
    private String numeroSolicitud;

    //TAREJTA
    private String fechaCreacion;
    private ENUMTipoTarjeta tipo;
    private int limiteTarjeta;
    private String estado;
    private SQL sql;
    
    public SolicitudBE(SQL sql, String nombreUsuario, String direccion, int salario, String numeroSolicitud, int tipo) {
        this.sql = sql;
        
        //DATOS USUARIO
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.salario = salario;
        this.numeroSolicitud = numeroSolicitud;
        
        
        //DATOS TAREJTA
        Date thisDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/y");
        this.fechaCreacion = dateFormat.format(thisDate);
        //1 Nacional, 2 Regional, 3 Internacional
        this.tipo = ENUMTipoTarjeta.values()[tipo];
        //Abajo que se escoja en base al tipo de arriba
        if (ENUMTipoTarjeta.values()[tipo] == ENUMTipoTarjeta.NACIONAL) {
            this.limiteTarjeta = 5000;
        } else if (ENUMTipoTarjeta.values()[tipo] == ENUMTipoTarjeta.REGIONAL) {
            this.limiteTarjeta = 10000;
        } else {
           this.limiteTarjeta = 20000;
        }
        this.estado = "TRAMITE";
        
        procesarTramite();
    }
    
    @Override
    public void procesarTramite() {
        this.sql.getConnection();
        //Meter datos del usuario
        System.out.println("Datos usuario");
        System.out.println("INSERT INTO usuario (nombre, direccion, salario) VALUES ('" + nombreUsuario
                + "', '" + direccion + "','" + salario + "');");
        String comandoUsuario = "INSERT INTO usuario (nombre, direccion, salario) VALUES ('" + nombreUsuario
                + "', '" + direccion + "','" + salario + "');";
        
        
        //Meter datos a la tarjeta
        System.out.println("Datos Tarjeta");
        System.out.println("INSERT INTO tarjeta (tipo, limite, estado) VALUES ('" + tipo
                + "', '" + limiteTarjeta + "','" + estado + "');");
        String comandoTarjeta = "INSERT INTO tarjeta (tipo, limite, estado) VALUES ('" + tipo
                + "', '" + limiteTarjeta + "','" + estado + "');";
        
        sql.escribirDatosSolicitudSQL(comandoUsuario, comandoTarjeta);
        
    }
}
