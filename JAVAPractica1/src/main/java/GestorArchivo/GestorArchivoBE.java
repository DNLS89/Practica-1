package GestorArchivo;

import GestorTarjetasBE.AutorizacionBE;
import GestorTarjetasBE.CancelacionBE;
import GestorTarjetasBE.ConsultaBE;
import GestorTarjetasBE.MovimientoBE;
import GestorTarjetasBE.SolicitudBE;
import SQL.SQL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GestorArchivoBE {

    //Abajo son arreglos en donde insgra la autorizacion dividida
//    private String[] solicitud ;
//    private String[] movimiento;
//    private String tarjetaAConsultar;
//    private String tarjetaAAutorizar;
//    private String tarjetaACancelar;
    private String[][] totalProcesos = new String[5][];
    private int velocidadProcesamiento;
    private File fileSalida;
    private File fileEntrada;
    private SQL sql;
    private boolean pathDefinidoSalida = false;
    private JLabel txtNoSolicitud;

    private String path = "";

    public GestorArchivoBE(SQL sql) {
        fileSalida = new File("");
        fileEntrada = new File("");
        this.sql = sql;
        //System.out.println("Canonical: " + file.getCanonicalPath());
        //System.out.println("Absoluto: " + file.getAbsolutePath());
        //this.sql = sql;

        //imprimirPruebaLinea();
        //System.out.println("FIN");
    }

    public void setTxtNoSolicitud(JLabel txtNoSolicitud) {
        this.txtNoSolicitud = txtNoSolicitud;
    }

    public File getFileSalida() {
        return fileSalida;
    }

    public void setFileSalida(File file) {
        this.fileSalida = file;
    }

    public boolean getPathDefinidoSalida() {
        return pathDefinidoSalida;
    }

    public void setPathDefinidoSalida(boolean pathDefinido) {
        this.pathDefinidoSalida = pathDefinido;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void cargarArchivo() {

    }

    private void procesar() {

    }

    private void indicarProcesamiento() {

    }

    public void abrirArchivo() {
        //JFileChooser fileChooser = new JFileChooser();

        //int response = fileChooser.showOpenDialog(null); //select file to open
//        if (response == JFileChooser.APPROVE_OPTION) {
//            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
//            String pathEntrada = file.toString();
//            limpiarProcesos(pathEntrada);
//        }
        //Experimentacion
        String path = "ArchivoEntrada";
        limpiarProcesos(path);

    }

    public void limpiarProcesos(String pathEntrada) {
        String linea;
        //String path = "ArchivoEntrada";
        try {
//            

            BufferedReader reader = new BufferedReader(new FileReader(pathEntrada));
            //int numeroLinea = 0;
            //Abajo limpia y divide los procesos y los ingresa a un arreglo
            while ((linea = reader.readLine()) != null) {
                String[] tramite;
                //System.out.println(linea);
                linea = linea.substring(0, (linea.length() - 2));
                if (linea.startsWith("SO")) {
                    linea = linea.substring(10);
                    linea = linea.replace("\"", "");
                    tramite = linea.split(",");
                    solicitar(tramite);
                } else if (linea.startsWith("MO")) {
                    linea = linea.substring(11);
                    linea = linea.replace("\"", "");
                    tramite = linea.split(",");
                    movimiento(tramite);
                    //movimiento = linea.split(",");
                } else if (linea.startsWith("CO")) {
                    linea = linea.substring(18);
                    tramite = linea.split(",");
                    consultar(tramite);
                    //tarjetaAConsultar = linea;
                } else if (linea.startsWith("AUTO")) {
                    linea = linea.substring(21);
                    tramite = linea.split(",");
                    autorizar(tramite);
                    //tarjetaAAutorizar = linea;
                } else if (linea.startsWith("CA")) {
                    linea = linea.substring(20);
                    tramite = linea.split(",");
                    //tarjetaACancelar = linea;
                    cancelar(tramite);
                }
                //linea = linea.substring(0, (linea.length() - 2));

                //Lo de abajo es para que meta la división de cada línea en un mismo arreglo
                //splitProcesos(linea, numeroLinea);
                //numeroLinea++;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error lectura archivo");
            e.printStackTrace();
        }
    }

    public boolean comprobarTengaValores(String[] tramite) {
        
        for (String elemento : tramite) {
            if (elemento.isBlank()) {
                JOptionPane.showMessageDialog(null, "Hay datos mal ingresados en el archivo de entrada", "ERROR", JOptionPane.PLAIN_MESSAGE);
                return false;
            } else {
               // System.out.println("Elemento " + elemento);
            }
        }
        return true;
    }
    
    public void movimiento(String[] tramite) {
        if (comprobarTengaValores(tramite)) {
            //CARGO  GASTAR 0,   ABONO 1
            int indiceMovimiento;
            if (tramite[2].equals("CARGO")) {
                indiceMovimiento = 0;
            } else {
                indiceMovimiento = 1;
            }
            BigDecimal monto = new BigDecimal(tramite[5]);
            
            MovimientoBE movimientoBE = new MovimientoBE(sql, indiceMovimiento, tramite[1], tramite[0], tramite[4], monto, tramite[3]);
            movimientoBE.procesarTramite();
        }
    }

    public void consultar(String[] tramite) {
        if (comprobarTengaValores(tramite)) {
            ConsultaBE consultaBE = new ConsultaBE(sql, tramite[0], this);
            consultaBE.procesarTramite();
        }

    }

    public void cancelar(String[] tramite) {
        if (comprobarTengaValores(tramite)) {
            CancelacionBE cancelacionBE = new CancelacionBE(tramite[0], sql);
            cancelacionBE.procesarTramite();
        }
    }

    public void autorizar(String[] tramite) {
        if (comprobarTengaValores(tramite)) {
            int numeroSolicitud = Integer.valueOf(tramite[0]);
            AutorizacionBE autorizacionBE = new AutorizacionBE(sql, numeroSolicitud);
        }
    }

    public void solicitar(String[] tramite) {

        if (comprobarTengaValores(tramite)) {
            //QL sql, String nombreUsuario, String direccion, int salario, int tipo, JLabel txtNoSolicitud
            //1 Nacional, 2 Regional, 3 Internacional
            int tipo;
            if (tramite[2].equals("NACIONAL")) {
                tipo = 1;
            } else if (tramite[2].equals("REGIONAL")) {
                tipo = 2;
            } else {
                tipo = 3;
            }

            double salario1 = Double.parseDouble((tramite[4]));
            int salario = (int) Math.round(salario1);
            System.out.println("Salario " + salario);

            SolicitudBE solicitudBE = new SolicitudBE(sql, tramite[3], tramite[5], salario, tipo, false);
        }
    }

    public int contadorProcesos() {
        int totalLineas = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ArchivoEntrada"));
            String linea;

            //Abajo cuenta TOTAL PROCESOS, los limpia y los ingresa a un arreglo
            while ((linea = reader.readLine()) != null) {
                totalLineas++;
            }
            reader.close();
            System.out.println("Número de líneas: " + totalLineas);
            return totalLineas;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalLineas;
    }

    public void splitProcesos(String linea, int numeroLinea) {
        String[] lineaDiv = linea.split(",");
        totalProcesos[numeroLinea] = lineaDiv;
    }

    public void imprimirPruebaLinea() {
        for (String[] numeroProceso : totalProcesos) {
            for (String columnaProceso : numeroProceso) {
                System.out.println(columnaProceso);
            }
            System.out.println("--------------------------------------");
        }
    }
}
