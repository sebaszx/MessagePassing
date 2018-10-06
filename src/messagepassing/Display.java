/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Sebas
 */
public class Display {

    public static String MostrarSistema() {
        String Estado = "";
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size() - 2; i++) {
            String Titulo = SingletonConfiguracion.ListaDeProcesos.get(i).identificadorProceso;
            String suscrito = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).suscrito);
            String Bloqueado = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).bloqueado);
            Estado += "Titulo: " + Titulo + " \n" + "Suscrito: " + suscrito + " \n" + "Bloqueado: " + Bloqueado;
            Estado += "\n Enviados: ";
            for (int a = 0; a < SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.size(); a++) {
                if (SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.size() != 0) {
                    String idEmisor = SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).identificadorEmisor;
                    String idReceptor = SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).identificadorReceptor;
                    String Tipo = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).tipo);
                    String Largo = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).largo);
                    String Contenido = SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).Contenido;
                    String prioridad = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).prioridad);
                    String Recibido = String.valueOf(SingletonConfiguracion.ListaDeProcesos.get(i).enviados.colaMensajes.get(a).Recibido);
                    Estado += "\n ID Emisor: " + idEmisor + " Receptor: " + idReceptor + " Tipo: " + Tipo + " Largo: " + Largo + "\n Contenido: " + Contenido + " Prioridad: " + prioridad + " Recibido: " + Recibido + "\n";
                }
            }
            Estado += "\n********************************************\n";
        }

        Estado += "\nMailBox del sistema";
        // para el mailbox genral
        for (int i = 0; i < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); i++) {
            String idEmisor = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).identificadorEmisor;
            String idReceptor = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).identificadorReceptor;
            String Tipo = String.valueOf(SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).tipo);
            String Largo = String.valueOf(SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).largo);
            String Contenido = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).Contenido;
            String prioridad = String.valueOf(SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).prioridad);
            String Recibido = String.valueOf(SingletonConfiguracion.mailboxGeneral.colaMensajes.get(i).Recibido);
            Estado += "\n ID Emisor: " + idEmisor + " Receptor: " + idReceptor + " Tipo: " + Tipo + " Largo: " + Largo + "\n Contenido: " + Contenido + " Prioridad: " + prioridad + " Recibido: " + Recibido;
            Estado += "\n----------------------------------\n";
        }
        Estado += "\n********************************************\n";
        Estado+= "\nProcesos Bloqueados para Direccionamiento Indirecto\n";
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesosBloqueados.size(); i++) {
            String idEmisor = SingletonConfiguracion.ListaDeProcesosBloqueados.get(i).identificadorProceso;
            String Bloqueado = String.valueOf(SingletonConfiguracion.ListaDeProcesosBloqueados.get(i).bloqueado);
            Estado += "\n ID Emisor: " + idEmisor + " Estado: " + Bloqueado;
            Estado += "\n----------------------------------\n";
        }
        return Estado;
    }

    public static void getallreceived(JTextArea escribe) {
        String Estado = "";
        ArrayList<Proceso> Novacios = new ArrayList<Proceso>();
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
            if (!SingletonConfiguracion.ListaDeProcesos.get(i).cola.colaMensajes.isEmpty()) {
                Novacios.add(SingletonConfiguracion.ListaDeProcesos.get(i));
            }
        }

        for (int i = 0; i < Novacios.size(); i++) {
            for (int j = 0; j < Novacios.get(i).cola.colaMensajes.size(); j++) {

                escribe.append("Mensajes Recibidos\n");
                escribe.append("Titulo :" + Novacios.get(i).identificadorProceso + "\n");
                escribe.append("ID Emisor: " + Novacios.get(i).cola.colaMensajes.get(j).identificadorEmisor + "\n");
                escribe.append("ID Receptor: " + Novacios.get(i).cola.colaMensajes.get(j).identificadorReceptor + "\n");
                escribe.append("Contenido: " + Novacios.get(i).cola.colaMensajes.get(j).Contenido + "\n");
                escribe.append("Largo: " + String.valueOf(Novacios.get(i).cola.colaMensajes.get(j).largo) + "\n");
                escribe.append("Prioridad: " + String.valueOf(Novacios.get(i).cola.colaMensajes.get(j).prioridad) + "\n");
                escribe.append("Tipo: " + String.valueOf(Novacios.get(i).cola.colaMensajes.get(j).tipo) + "\n");
                escribe.append("Recibido: " + String.valueOf(Novacios.get(i).cola.colaMensajes.get(j).Recibido) + "\n");
                escribe.append("-----------------------------------------------\n");

            }
        }
        //escribe.append(Estado);

    }
}
