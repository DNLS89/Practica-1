package ReportesBE;

import GestorArchivo.GestorArchivoBE;
import SQL.SQL;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ListadoSolicitudesBE extends Reporte {

    private SQL sql;
    private Connection connection;
    private JTable tabla;
    private GestorArchivoBE gestorArchivoBE;

    //Variables para filtrar
    private boolean filtrarTipoTarjeta;
    private String tipoTarjeta;
    private boolean filtrarSaldoMayorA;
    private int saldoMayorA;

    private boolean filtrarEstado;
    private String estadoTarjeta;
    private boolean filtrarFecha;
    private String fechaInicial;
    private String fechaFinal;

    private boolean ejecucionGUI = false;
    private String comandoListadoTarjetas = "SELECT numero_tarjeta, fecha_ultima_modificacion, tipo, nombre, "
            + "salario, direccion, estado FROM usuario u, tarjeta t WHERE (u.id_usuario = t.numero_solicitud)";

    public ListadoSolicitudesBE(SQL sql, String numeroTarjeta, boolean filtrarTipoTarjeta, String tipoTarjeta,
            boolean filtrarSaldoMayorA, int saldoMayorA, boolean filtrarEstado, String estadoTarjeta,
            boolean filtrarFecha, String fechaInicial, String fechaFinal, boolean ejecucionGUI, GestorArchivoBE gestorArchivoBE) {

        this.sql = sql;
        this.connection = sql.getConnection();
        this.filtrarTipoTarjeta = filtrarTipoTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.filtrarSaldoMayorA = filtrarSaldoMayorA;
        this.saldoMayorA = saldoMayorA;

        this.filtrarEstado = filtrarEstado;
        this.estadoTarjeta = estadoTarjeta;
        this.filtrarFecha = filtrarFecha;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;

        this.ejecucionGUI = ejecucionGUI;
        this.gestorArchivoBE = gestorArchivoBE;
    }

    public ListadoSolicitudesBE(SQL sql, boolean filtrarTipoTarjeta, String tipoTarjeta,
            boolean filtrarSaldoMayorA, int saldoMayorA, boolean filtrarEstado,
            String estadoTarjeta, boolean filtrarFecha, String fechaInicial, String fechaFinal,
            boolean ejecucionGUI, GestorArchivoBE gestorArchivoBE, JTable tabla) {

        this.sql = sql;
        this.connection = sql.getConnection();
        this.filtrarTipoTarjeta = filtrarTipoTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.filtrarSaldoMayorA = filtrarSaldoMayorA;
        this.saldoMayorA = saldoMayorA;

        this.filtrarEstado = filtrarEstado;
        this.estadoTarjeta = estadoTarjeta;
        this.filtrarFecha = filtrarFecha;
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;

        this.ejecucionGUI = ejecucionGUI;
        this.gestorArchivoBE = gestorArchivoBE;
        this.tabla = tabla;
    }

    @Override
    public void procesar() {

        crearComandoParaFiltrar();
        if (ejecucionGUI) {
            extraerDatosTabla();
        }
        //Generar HTML
        try {
            generarHTML();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void extraerDatosTabla() {
        DefaultTableModel tableModel = (DefaultTableModel) tabla.getModel();
        tableModel.setRowCount(0);

        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoListadoTarjetas);
            //Existe el numero de tarjeta
            while (resultSet.next()) {
                String numeroTarjeta = resultSet.getString("numero_tarjeta");
                String fechaUltMod = resultSet.getString("fecha_ultima_modificacion");
                String tipoTarjeta2 = resultSet.getString("tipo");
                String nombreUsuario = resultSet.getString("nombre");
                int salario = resultSet.getInt("salario");
                String direccionUsuario = resultSet.getString("direccion");
                String estadoTarjeta = resultSet.getString("estado");

                tableModel.addRow(new Object[]{numeroTarjeta, fechaUltMod, tipoTarjeta2, 
                    nombreUsuario, salario, direccionUsuario, estadoTarjeta});

            }

        } catch (Exception e) {
            System.out.println("Error ingresando valores tabla GUI");
            e.printStackTrace();
        }
    }

    public void generarHTML() throws IOException {
        File h;
        //No tarjeta, tipo tarjeta, limite, nombre, dirección, estado de tarjeta
        String html = ("<div><h1>Listado Solicitudes</h1><p></p>");
        if (gestorArchivoBE.getPathDefinido()) {
            h = new File(gestorArchivoBE.getFile().getCanonicalPath() + "/ListadoSolicitudes.html");
        } else {
            h = new File("ListadoSolicitudes.html");
        }
        //File h = new File("Consulta.html"); 
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(h, true));
            bw.write(html);
            bw.write("<table bgcolor=\"black\">");
            bw.write("<tr bgcolor=\"grey\">");
            bw.write("<th>Número de Tajeta</th>");
            bw.write("<th>Fecha Ult. Mod.</th>");
            bw.write("<th>Tipo de Tarjeta</th>");
            bw.write("<th>Nombre Usuario</th>");
            bw.write("<th>Salario</th>");
            bw.write("<th>Dirección Usuario</th>");
            bw.write("<th>Estado Tarjeta</th>");

            bw.write("</tr>");

            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoListadoTarjetas);
            //Existe el numero de tarjeta
            while (resultSet.next()) {
                String numeroTarjeta = resultSet.getString("numero_tarjeta");
                String fechaUltMod = resultSet.getString("fecha_ultima_modificacion");
                String tipoTarjeta2 = resultSet.getString("tipo");
                String nombreUsuario = resultSet.getString("nombre");
                int salario = resultSet.getInt("salario");
                String direccionUsuario = resultSet.getString("direccion");
                String estadoTarjeta = resultSet.getString("estado");

                bw.write("<tr bgcolor=\"lightgrey\" align=\"center\">");
                bw.write("<td>" + numeroTarjeta + "</td>");
                bw.write("<td>" + fechaUltMod + "</td>");
                bw.write("<td>" + tipoTarjeta2 + "</td>");
                bw.write("<td>" + nombreUsuario + "</td>");
                bw.write("<td>" + salario + "</td>");
                bw.write("<td>" + direccionUsuario + "</td>");
                bw.write("<td>" + estadoTarjeta + "</td>");

            }

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

    public void crearComandoParaFiltrar() {

        if (filtrarTipoTarjeta) {
            comandoListadoTarjetas += " AND (tipo = \"" + tipoTarjeta + "\")";
        }
        if (filtrarSaldoMayorA) {
            comandoListadoTarjetas += " AND (saldo > " + saldoMayorA + ")";
        }
        if (filtrarEstado) {
            comandoListadoTarjetas += " AND (estado = \"" + estadoTarjeta + "\")";
        }
        if (filtrarFecha) {
            //Set rango, probar con esto
        }
        //System.out.println("COmando creado: " + comandoListadoTarjetas);
    }
}
