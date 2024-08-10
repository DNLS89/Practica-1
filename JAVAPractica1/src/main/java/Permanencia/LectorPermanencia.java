package Permanencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LectorPermanencia {
    
    //Abajo son arreglos en donde insgra la autorizacion dividida
//    private String[] solicitud ;
//    private String[] movimiento;
//    private String tarjetaAConsultar;
//    private String tarjetaAAutorizar;
//    private String tarjetaACancelar;
    private String[][] totalProcesos = new String[5][];
    
    public LectorPermanencia() {

        limpiarProcesos();
        imprimirPruebaLinea();
        System.out.println("FIN");
    }
    
    public void limpiarProcesos() {
        String linea;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ArchivoEntrada"));
            int numeroLinea = 0;
            //Abajo limpia y divide los procesos y los ingresa a un arreglo
            while ((linea = reader.readLine()) != null) {
                //System.out.println(linea);
                if (linea.startsWith("SO")) {
                    //System.out.println("Limpiando Solicitud");
                    linea = linea.substring(10);
                    linea = linea.replace("\"", "");
                    //solicitud = linea.split(",");
                } else if (linea.startsWith("MO")) {
                    //System.out.println("Limpiando Movimiento");
                    linea = linea.substring(11);
                    linea = linea.replace("\"", "");
                    //movimiento = linea.split(",");
                } else if (linea.startsWith("CO")) {
                    //System.out.println("Limpiando Consulta");
                    linea = linea.substring(18);
                    //tarjetaAConsultar = linea;
                } else if (linea.startsWith("AUTO")) {
                    //System.out.println("Limpiando Autorizacion");
                    linea = linea.substring(21);
                    //tarjetaAAutorizar = linea;
                } else if (linea.startsWith("CA")) {
                    //System.out.println("Limpiando Cancelacion");
                    linea = linea.substring(20);
                    //tarjetaACancelar = linea;
                }
                linea = linea.substring(0, (linea.length()-2));
                //System.out.println(linea);
                
                //Lo de abajo es para que meta la división de cada línea en un mismo arreglo
                splitProcesos(linea, numeroLinea);
                numeroLinea++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    
    public int contadorProcesos(){
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
}
