
package Tramite;

import GestorTarjetasFE.ENUMTipoTarjeta;
import SQL.SQL;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class SolicitudBE extends Tramite{
    //USUARIO
    private String nombreUsuario;
    private int salario;
    private String direccion;
    private String numeroSolicitud;

    //TAREJTA
    private ENUMTipoTarjeta tipo;
    private int limiteTarjeta;
    private String estado;
    private SQL sql;
    private JLabel txtNoSolicitud;
    
    public SolicitudBE(SQL sql, String nombreUsuario, String direccion, int salario, int tipo, JLabel txtNoSolicitud) {
        this.sql = sql;
        
        //DATOS USUARIO
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.salario = salario;
        this.txtNoSolicitud = txtNoSolicitud;
        
        
        //DATOS TAREJTA
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
        String comandoUsuario = "INSERT INTO usuario (nombre, direccion, salario) VALUES ('" + nombreUsuario
                + "', '" + direccion + "','" + salario + "');";
        
        //Meter datos a la tarjeta
        String comandoTarjeta = "INSERT INTO tarjeta (tipo, limite, estado) VALUES ('" + tipo
                + "', '" + limiteTarjeta + "','" + estado + "');";
        
        //Abajo muestra el último numero de solicitud
        numeroSolicitud = sql.escribirDatosSolicitudSQL(comandoUsuario, comandoTarjeta);
        if (!numeroSolicitud.equals("")) {
            txtNoSolicitud.setText("Su número de solicitud es: " + numeroSolicitud);
        }
        
        JOptionPane.showMessageDialog(null, "Solicitud Procesada", "", JOptionPane.PLAIN_MESSAGE);
    }
}
