/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Sebas
 */
public class SingletonConfiguracion {

    // para tener las variables globales
    // los pongo con valores por si en la interfaz no ponen nada y dan next para que no se caiga, pero no sé si las configuraciones tienen sentido
    public static boolean sendBlocking = false;
    public static boolean sendNonBlocking = false;
    public static boolean recieveBlocking = false;
    public static boolean recieveNonBlocking = false;

    public static boolean recieveLlegada = false;
    public static TipoContenido Formato = TipoContenido.valueOf("Texto");
    public static int tamañoLimite = 0;// es el encarago de decir que si es 0 en variable 
    public static int cantidadProcesos;

    public static boolean direccionamientoDirecto;
    public static boolean directoRecieveImplicito;
    public static boolean directoRecieveExplicito;
    public static boolean indirectoEstatico;
    public static boolean indirectoDinamico;
    public static boolean FIFO;
    public static boolean prioridad;
    public static ArrayList<Proceso> ListaDeProcesos = new ArrayList<Proceso>();
    public static String url;
    public static String mensaje;
    public static Mailbox mailboxGeneral= new Mailbox();
    public static ArrayList<Proceso> ListaDeProcesosBloqueados = new ArrayList<Proceso>();
    public static File f2;

    
    
    
    
    
    
}
