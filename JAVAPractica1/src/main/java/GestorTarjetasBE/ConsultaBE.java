package GestorTarjetasBE;

import GestorArchivo.GestorArchivoBE;
import SQL.SQL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConsultaBE extends Tramite {

    private SQL sql;
    private Connection connection;
    private String numeroTarjeta;

    //No tarjeta, tipo tarjeta, limite, nombre, dirección, estado de tarjeta
    private int numeroSolicitud;
    private String tipoTarjeta;
    private int limiteTarjeta;
    private String estadoTarjeta;
    private String nombreUsuarioTarjeta;
    private String direccionUsuarioTarjeta;
    private GestorArchivoBE gestorArchivoBE;

    public ConsultaBE(SQL sql, String numeroTarjeta, GestorArchivoBE gestorArchivoBE) {
        this.sql = sql;
        this.connection = sql.getConnection();
        this.numeroTarjeta = numeroTarjeta;
        this.gestorArchivoBE = gestorArchivoBE;
    }

    @Override
    public void procesarTramite() {
        if (comprobarTarjetaExiste()) {
            extraerDatosParaHTML();
            try {
                guardarConsultaHTML();
            } catch (IOException ex) {
                Logger.getLogger(ConsultaBE.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public boolean comprobarTarjetaExiste() {
        String comandoTarjeta = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";
        
        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoTarjeta);
            //Existe el numero de tarjeta
            if (resultSet.next()) {
                //Existe el número de tarjeta
                return true;
                
            } else {
                //No existe el número de tarjeta
                JOptionPane.showMessageDialog(null, "El número de tarjeta no existe o el formato es erróneo", "", JOptionPane.PLAIN_MESSAGE);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void extraerDatosParaHTML() {
        //No tarjeta, tipo tarjeta, limite, nombre, dirección, estado de tarjeta
        String comandoDatosTarjeta = "SELECT * FROM tarjeta WHERE numero_tarjeta like \"" + numeroTarjeta + "\"";

        try {
            //Estraer datos usuario según el número de tarjeta, para esto extraer el numeor de solocitud
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoDatosTarjeta);
            if (resultSet.next()) {
                numeroSolicitud = resultSet.getInt("numero_solicitud");
                tipoTarjeta = resultSet.getString("tipo");
                limiteTarjeta = resultSet.getInt("limite");
                estadoTarjeta = resultSet.getString("estado");
            }

            String comandoDatosUsuario = "SELECT * FROM usuario WHERE id_usuario like \"" + numeroSolicitud + "\"";
            resultSet = statementInsert.executeQuery(comandoDatosUsuario);
            if (resultSet.next()) {
                nombreUsuarioTarjeta = resultSet.getString("nombre");
                direccionUsuarioTarjeta = resultSet.getString("direccion");
            }

        } catch (Exception e) {
            System.out.println("Error extraer datos consulta");
            e.printStackTrace();
        }
    }

    public void guardarConsultaHTML() throws IOException {
        //Ahora guardar a HTML
        File h;
        //No tarjeta, tipo tarjeta, limite, nombre, dirección, estado de tarjeta
        String html = ("<div><h1>Consulta Tarjeta \"" + numeroTarjeta + "\" </h1><p></p>");
        if (gestorArchivoBE.getPathDefinido()) {
            h = new File(gestorArchivoBE.getFile().getCanonicalPath() + "/Consultas1.html");
        } else {
            h = new File("Consultas1.html");
        }
        //File h = new File("Consulta.html"); 
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(h, true));
            bw.write(html);
            bw.write("<table bgcolor=\"black\">");
            bw.write("<tr bgcolor=\"grey\">");
            bw.write("<th>Número de Tajeta</th>");
            bw.write("<th>Tipo de Tarjeta</th>");
            bw.write("<th>Límite</th>");
            bw.write("<th>Estado</th>");
            bw.write("<th>Nombre Usuario</th>");
            bw.write("<th>Dirección Usuario</th>");
            bw.write("</tr>");

            bw.write("<tr bgcolor=\"lightgrey\" align=\"center\">");
            bw.write("<td>" + numeroTarjeta + "</td>");
            bw.write("<td>" + tipoTarjeta + "</td>");
            bw.write("<td>" + limiteTarjeta + "</td>");
            bw.write("<td>" + estadoTarjeta + "</td>");
            bw.write("<td>" + nombreUsuarioTarjeta + "</td>");
            bw.write("<td>" + direccionUsuarioTarjeta + "</td>");

            bw.write("</tr>");

            bw.write("</table>");
            bw.write("<br>");
            bw.write("</br>");

            bw.close();
            JOptionPane.showMessageDialog(null, "HTML Realizado Exitosamente", "Consulta Realizada", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
