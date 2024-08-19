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

public class EstadoCuentaBE extends Reporte {

    private SQL sql;
    private Connection connection;
    private String numeroTarjeta;
    private JTable tabla;
    private GestorArchivoBE gestorArchivoBE;

    //Variables para filtrar
    //4 datos que filtran no. tarjeta, tipo, saldos mayores a, interés mayor a
    private boolean filtrarTipoTarjeta;
    private String tipoTarjeta;
    private boolean filtrarSaldoMayorA;
    private int saldoMayorA;
    private boolean filtrarInteresMayorA;
    private int interesMayorA;
    private boolean ejecucionGUI = false;
    private String comandoEstadoCuenta;

    public EstadoCuentaBE(SQL sql, String numeroTarjeta, boolean filtrarTipoTarjeta, String tipoTarjeta,
            boolean filtrarSaldoMayorA, int saldoMayorA, boolean filtrarInteresMayorA,
            int interesMayorA, boolean ejecucionGUI, GestorArchivoBE gestorArchivoBE) {

        
       comandoEstadoCuenta = "SELECT numero_tarjeta, tipo, nombre, direccion, tipoMov, fecha_movimiento, "
            + "descripcion_movimiento, establecimiento_movimiento, monto, interes, saldo FROM usuario u, "
            + "movimiento m, tarjeta t WHERE (u.id_usuario = m.id_usuario AND m.numero_solicitud = t.numero_solicitud)";
        this.sql = sql;
        this.connection = sql.getConnection();
        this.numeroTarjeta = numeroTarjeta;
        this.filtrarTipoTarjeta = filtrarTipoTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.filtrarSaldoMayorA = filtrarSaldoMayorA;
        this.saldoMayorA = saldoMayorA;
        this.filtrarInteresMayorA = filtrarInteresMayorA;
        this.interesMayorA = interesMayorA;
        this.ejecucionGUI = ejecucionGUI;
        this.gestorArchivoBE = gestorArchivoBE;
    }

    public EstadoCuentaBE(SQL sql, String numeroTarjeta, boolean filtrarTipoTarjeta, String tipoTarjeta,
            boolean filtrarSaldoMayorA, int saldoMayorA, boolean filtrarInteresMayorA,
            int interesMayorA, boolean ejecucionGUI, GestorArchivoBE gestorArchivoBE, JTable tabla) {

        comandoEstadoCuenta = "SELECT numero_tarjeta, tipo, nombre, direccion, tipoMov, fecha_movimiento, "
            + "descripcion_movimiento, establecimiento_movimiento, monto, interes, saldo FROM usuario u, "
            + "movimiento m, tarjeta t WHERE (u.id_usuario = m.id_usuario AND m.numero_solicitud = t.numero_solicitud)";
        this.sql = sql;
        this.connection = sql.getConnection();
        this.numeroTarjeta = numeroTarjeta;
        this.filtrarTipoTarjeta = filtrarTipoTarjeta;
        this.tipoTarjeta = tipoTarjeta;
        this.filtrarSaldoMayorA = filtrarSaldoMayorA;
        this.saldoMayorA = saldoMayorA;
        this.filtrarInteresMayorA = filtrarInteresMayorA;
        this.interesMayorA = interesMayorA;
        this.ejecucionGUI = ejecucionGUI;
        this.tabla = tabla;
        this.gestorArchivoBE = gestorArchivoBE;
    }

    @Override
    public void procesar() {

        if (comprobarTarjetaExiste()) {
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

    }

    public void extraerDatosTabla() {
        DefaultTableModel tableModel = (DefaultTableModel) tabla.getModel();
        tableModel.setRowCount(0);

        try {
            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoEstadoCuenta);
            //Existe el numero de tarjeta
            while (resultSet.next()) {
                String tipoTarjeta2 = resultSet.getString("tipo");
                String nombreUsuario = resultSet.getString("nombre");
                String direccionUsuario = resultSet.getString("direccion");
                String tipoMov = resultSet.getString("tipoMov");
                String fechaMov = resultSet.getString("fecha_movimiento");
                String descripcionMov = resultSet.getString("descripcion_movimiento");
                String establecimientoMov = resultSet.getString("establecimiento_movimiento");
                int montoMov = resultSet.getInt("monto");
                double interesesSaldo = resultSet.getDouble("interes");
                int saldo = resultSet.getInt("saldo");

                tableModel.addRow(new Object[]{numeroTarjeta, tipoTarjeta2, nombreUsuario, direccionUsuario,
                    tipoMov, fechaMov, descripcionMov, establecimientoMov, montoMov, interesesSaldo, saldo});

            }

        } catch (Exception e) {
            System.out.println("Error ingresando valores tabla GUI");
            e.printStackTrace();
        }
    }

    public void generarHTML() throws IOException {
        File h;
        //No tarjeta, tipo tarjeta, limite, nombre, dirección, estado de tarjeta
        String html = ("<div><h1>Estado Cuenta Tarjeta \"" + numeroTarjeta + "\" </h1><p></p>");
        if (gestorArchivoBE.getPathDefinido()) {
            h = new File(gestorArchivoBE.getFile().getCanonicalPath() + "/EstadoCuenta.html");
        } else {
            h = new File("EstadoCuenta.html");
        }
        //File h = new File("Consulta.html"); 
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(h, true));
            bw.write(html);
            bw.write("<table bgcolor=\"black\">");
            bw.write("<tr bgcolor=\"grey\">");
            bw.write("<th>Número de Tajeta</th>");
            bw.write("<th>Tipo de Tarjeta</th>");
            bw.write("<th>Nombre Usuario</th>");
            bw.write("<th>Dirección Usuario</th>");
            bw.write("<th>Tipo Movimiento</th>");
            bw.write("<th>Fecha Movimiento</th>");
            bw.write("<th>Descripción Movimiento</th>");
            bw.write("<th>Establecimiento Movimiento</th>");
            bw.write("<th>Monto Movimiento</th>");
            bw.write("<th>Intereses</th>");
            bw.write("<th>Saldo</th>");

            bw.write("</tr>");

            Statement statementInsert = connection.createStatement();
            ResultSet resultSet = statementInsert.executeQuery(comandoEstadoCuenta);
            //Existe el numero de tarjeta
            while (resultSet.next()) {
                //numeroTarjeta, tipoTarjeta2, nombreUsuario, direccionUsuario, 
//                    tipoMov, fechaMov, descripcionMov, establecimientoMov, montoMov, interesesSaldo, saldo

                String tipoTarjeta2 = resultSet.getString("tipo");
                String nombreUsuario = resultSet.getString("nombre");
                String direccionUsuario = resultSet.getString("direccion");
                String tipoMov = resultSet.getString("tipoMov");
                String fechaMov = resultSet.getString("fecha_movimiento");
                String descripcionMov = resultSet.getString("descripcion_movimiento");
                String establecimientoMov = resultSet.getString("establecimiento_movimiento");
                int montoMov = resultSet.getInt("monto");
                double interesesSaldo = resultSet.getDouble("interes");
                int saldo = resultSet.getInt("saldo");
                
                bw.write("<tr bgcolor=\"lightgrey\" align=\"center\">");
                bw.write("<td>" + numeroTarjeta + "</td>");
                bw.write("<td>" + tipoTarjeta2 + "</td>");
                bw.write("<td>" + nombreUsuario + "</td>");
                bw.write("<td>" + direccionUsuario + "</td>");
                bw.write("<td>" + tipoMov + "</td>");
                bw.write("<td>" + fechaMov + "</td>");
                bw.write("<td>" + descripcionMov + "</td>");
                bw.write("<td>" + establecimientoMov + "</td>");
                bw.write("<td>" + montoMov + "</td>");
                bw.write("<td>" + interesesSaldo + "</td>");
                bw.write("<td>" + saldo + "</td>");

            }

//            bw.write("<tr bgcolor=\"lightgrey\" align=\"center\">");
//            bw.write("<td>" + numeroTarjeta + "</td>");
//            bw.write("<td>" + tipoTarjeta + "</td>");
//            bw.write("<td>" + limiteTarjeta + "</td>");
//            bw.write("<td>" + estadoTarjeta + "</td>");
//            bw.write("<td>" + nombreUsuarioTarjeta + "</td>");
//            bw.write("<td>" + direccionUsuarioTarjeta + "</td>");
            bw.write("</tr>");

            bw.write("</table>");
            bw.write("<br>");
            bw.write("</br>");

            bw.close();
            JOptionPane.showMessageDialog(null, "HTML creado", "Consulta Realizada", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearComandoParaFiltrar() {
        comandoEstadoCuenta += " AND (numero_tarjeta = \"" + numeroTarjeta + "\")";

        if (filtrarTipoTarjeta) {
            comandoEstadoCuenta += " AND (tipo = \"" + tipoTarjeta + "\")";
        }
        if (filtrarSaldoMayorA) {
            comandoEstadoCuenta += " AND (saldo > " + saldoMayorA + ")";
        }
        if (filtrarInteresMayorA) {
            comandoEstadoCuenta += " AND (interes > " + interesMayorA + ")";
        }
        System.out.println("COmando creado: " + comandoEstadoCuenta);
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
}
