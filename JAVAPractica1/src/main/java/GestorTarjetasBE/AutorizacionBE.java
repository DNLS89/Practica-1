/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GestorTarjetasBE;

import SQL.SQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author debian
 */
public class AutorizacionBE extends Tramite {

    private SQL sql;
    private int numeroSolicitud;
    private String mensaje;
    private Connection connection;
    private String fechaCreacion;
    
    private final String valorInicialNacional = "4256 3102 6540 0000";
    private final String valorInicialRegional = "4256 3102 6540 0000";
    private final String valorInicialInternacional = "4256 3102 6540 0000";

    public AutorizacionBE(SQL sql, int numeroSolicitud) {
        this.sql = sql;
        this.connection = sql.getConnection();
        this.numeroSolicitud = numeroSolicitud;
        procesarTramite();
    }

    @Override
    public void procesarTramite() {
        if (comprobarCumpleRequisitos()) {
            cambiarEstado();
            cambiarNumeroTarjeta(tipoTarjeta());
            try {
                agregarFechaCreacion();
            } catch (ParseException ex) {
                Logger.getLogger(AutorizacionBE.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOptionPane.showMessageDialog(null, "La tajeta ha sido autorizada", "", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
        public boolean comprobarCumpleRequisitos() {
        String selectUsuario = "SELECT * FROM usuario WHERE id_usuario like " + numeroSolicitud + "";
        String selectTarjeta = "SELECT * FROM tarjeta WHERE numero_solicitud like " + numeroSolicitud + "";
        double salarioNecesarioUsuario = 0;
        String estadoTarjeta = "";
        int limiteTarjeta = 0;
        //4 comprobaciones:
        //Que exista el numero de solicitud
        //Salario mayor a 60%
        //No esté autorizada
        //No esté cancelada
        
        try {
            //Ingresa a cliente para verificar de que el valor de solicitud es válido
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(selectUsuario);
            //Existe el número de solicitud
            if (!resultSet.next()) {
                mensaje = "Número de solicitud inválido, no existente o erróneo";
                return false;
            }
            
            //Extrae el Salario
            resultSet = statementInsert.executeQuery(selectUsuario);
            while (resultSet.next()) {
                salarioNecesarioUsuario = (resultSet.getInt("salario")* 0.6);
            }
            
            //Ingresa a tarjeta y extrae los datos
            resultSet = statementInsert.executeQuery(selectTarjeta);
            while (resultSet.next()) {
                estadoTarjeta = resultSet.getString("estado");
                limiteTarjeta = resultSet.getInt("limite");
            }
            //Comprueba SALARIO MAYOR A 60%
            if (!(salarioNecesarioUsuario >= limiteTarjeta)) {
                mensaje = "Tú perfil no cumple con los requisitos para ser otorgada una tarjeta";
                return false;
            }

            //La tarjeta no esté autorizada/cancelada/
            if (estadoTarjeta.equals("ACTIVA")) {
                mensaje = "Esta tarjeta ya ha sido autorizada";
                return false;
            } else if (estadoTarjeta.equals("CANCELADA")) {
                mensaje = "Esta tarjeta está cancelada, no puede reactivarse";
                return false;
            } else if (!estadoTarjeta.equals("TRAMITE")) {
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al consultar a la DB al autorizar");
            e.printStackTrace();
        }
        
        return true;
    }
    
    public void agregarFechaCreacion() throws ParseException {
        Date thisDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date (thisDate.getTime()); 
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //this.fechaCreacion = dateFormat.format(thisDate);
        //Date date = dateFormat.parse(fechaCreacion);
        System.out.println("Cambiando fecha");
        //Los ingresa a la base de datos
        try {
            String comandoIngresarFechaCreacion = "UPDATE tarjeta set fecha_creacion = " + "\"" + sqlDate + "\"" 
                            + " WHERE numero_solicitud = " + "\"" + numeroSolicitud + "\"" + " ";
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(comandoIngresarFechaCreacion);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cambiarNumeroTarjeta(String tipoTarjeta) {
        
        //Verifica que haya un valor asignado a tarjetas del mismo tipo, si no eixste lo asigna, 
        //si existe encuentra el numero más grande según tipo de tarjeta (nacional, regional, internacional) 
        try {
            String verificarNumeroMasGrande = "SELECT * FROM tarjeta WHERE tipo like " + "\"" + tipoTarjeta + "\"" + " AND numero_tarjeta is not null";
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(verificarNumeroMasGrande);
            //Si no existe asigna un valor inicial
            if (!resultSet.next()) {
                //Asignar un valor inicial según el tipo de tarjeta (Nacional, Regional o Internacional)
                
                String comandoEscogerValorInicialTipo = "";
                if (tipoTarjeta.equals("NACIONAL")) {
                    comandoEscogerValorInicialTipo = "UPDATE tarjeta set numero_tarjeta = " + "\"" + valorInicialNacional + "\"" 
                            + " WHERE numero_solicitud = " + "\"" + numeroSolicitud + "\"" + " ";
                } else if (tipoTarjeta.equals("REGIONAL")) {
                    comandoEscogerValorInicialTipo = "UPDATE tarjeta set numero_tarjeta = " + "\"" + valorInicialRegional + "\"" 
                            + " WHERE numero_solicitud = " + "\"" + numeroSolicitud + "\"" + " ";
                } else {
                    comandoEscogerValorInicialTipo = "UPDATE tarjeta set numero_tarjeta = " + "\"" + valorInicialInternacional + "\"" 
                            + " WHERE numero_solicitud = " + "\"" + numeroSolicitud + "\"" + " ";
                }
                
//                String comandoUsuario = "INSERT INTO tarjeta where tipo like " + "\"" + tipoTarjeta + "\"" + " (numero_tarjeta) VALUES ('" + valorInicialNacional
//                + "');";
                statementInsert.executeUpdate(comandoEscogerValorInicialTipo);
            } else {
                //Acá es cuando ya se asignó un número a las tarjetas, extrae el valor más grande y le sumar 1 con el método
                String numeroTarjeta = "select max(numero_tarjeta) from tarjeta";
                statementInsert = connection.createStatement();
                resultSet = statementInsert.executeQuery(numeroTarjeta);
                String numeroTarjetaAnterior = ""; 
                
                if (resultSet.next()) {
                    numeroTarjetaAnterior = resultSet.getString("max(numero_tarjeta)");
                }
                
                //Escribir nuevo numero tarjeta
                String comandoIngresarNumeroTarjeta = "UPDATE tarjeta set numero_tarjeta = " + "\"" + nuevoNumeroTarjeta(numeroTarjetaAnterior) + "\"" 
                            + " WHERE numero_solicitud = " + "\"" + numeroSolicitud + "\"" + " ";
                statementInsert.executeUpdate(comandoIngresarNumeroTarjeta);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cambiarEstado(){
        try {
            String cambioEstadoTarjeta = "UPDATE tarjeta set estado = \"ACTIVA\" where numero_solicitud = " + numeroSolicitud;
            Statement statementInsert = connection.createStatement();
            statementInsert.executeUpdate(cambioEstadoTarjeta);
        } catch (SQLException e) {
            System.out.println("Error autorizando");
            e.printStackTrace();
        }
    }
    
    public String tipoTarjeta() {
        //Averigua el tipo de tarjeta
        String tipoTarjeta = "";
        try {
            String numeroTarjeta = "SELECT * FROM tarjeta WHERE numero_solicitud like " + numeroSolicitud + "";
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(numeroTarjeta);
            if (resultSet.next()) {
                tipoTarjeta = resultSet.getString(("tipo"));
            }
        } catch (Exception e) {
            System.out.println("Error buscar tipo tarjeta");
            e.printStackTrace();
        }
        return tipoTarjeta;
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
        divNumeroTarjeta[13] = divNumeroNUevo[0];
        divNumeroTarjeta[15] = divNumeroNUevo[1];
        divNumeroTarjeta[16] = divNumeroNUevo[2];
        divNumeroTarjeta[17] = divNumeroNUevo[3];
        divNumeroTarjeta[18] = divNumeroNUevo[4];
        String nuevoNumeroTarjeta = "";
        for (String numero : divNumeroTarjeta) {
            nuevoNumeroTarjeta += numero;
        }

        return nuevoNumeroTarjeta;
    }
}
