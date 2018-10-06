/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebas
 */
public class Proceso {

    Mensaje sms;// creo que es innecesario porque ya los mailbox tienen los sms 
    String identificadorProceso;
    Mailbox cola;// es el de recibir mensajes
    Mailbox enviados;// los mensajes enviados de este proceso
    boolean bloqueado = false; //Cuando se crea el proceso, no está bloqueado
    boolean suscrito = false; //Cuando se crea el proceso, no está suscrito a ningun mailbox.

    public Proceso() {
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public boolean isSuscrito() {
        return suscrito;
    }

    public void setSuscrito(boolean suscrito) {
        this.suscrito = suscrito;
    }

    public Proceso(Mensaje sms, String identificadorProceso, Mailbox cola, Mailbox enviados) {
        this.sms = sms;
        this.identificadorProceso = identificadorProceso;
        this.cola = cola;
        this.enviados = enviados;

    }

    public Mensaje getSms() {
        return sms;
    }

    public void setSms(Mensaje sms) {
        this.sms = sms;
    }

    public String getIdentificadorProceso() {
        return identificadorProceso;
    }

    public void setIdentificadorProceso(String identificadorProceso) {
        this.identificadorProceso = identificadorProceso;
    }

    public Mailbox getCola() {
        return cola;
    }

    public void setCola(Mailbox cola) {
        this.cola = cola;
    }

    public Mailbox getEnviados() {
        return enviados;
    }

    public void setEnviados(Mailbox enviados) {
        this.enviados = enviados;
    }

    // send y receive metodos y estos reciben algo tipo proceso
    public static void send(Mensaje sms) {
        Proceso Encontrado = EncontrarProceso(sms.identificadorEmisor);//proceso que mandó
      //  System.out.println(Encontrado.identificadorProceso + "!!!!");
        Proceso Encontrado2 = EncontrarProceso(sms.identificadorReceptor);// proceso que va a recibir
        //posible esqueleto
        if (SingletonConfiguracion.sendBlocking == true) {
            System.out.println("Entro a sendblocking");
            //Direccionamiento directo e indirecto
            if (SingletonConfiguracion.direccionamientoDirecto == true) {
                System.out.println("Entro a Direccionamiento Directo");
                // yo llego y meto el sms en el mailbox del receptor, despues bloqueo el proceso que lo mandó y dentro del sms 
                // va explicitamente quien lo mandó 
                // ya el sms tiene el emisor explicito siempre si no fuera directo se le cambia a null para que no se sepa quien lo envio 
                // y se los mandó al mailbox respectivo.
                System.out.println(Encontrado.bloqueado);
                if (Encontrado.bloqueado == false) {
                    System.out.println("Entro aqui!!");
                    Encontrado.enviados.colaMensajes.add(sms);
                    Encontrado2.cola.colaMensajes.add(sms);
                    Encontrado2.bloqueado = false;// esto es nuevo, esto es porque si el proceso esta bloqueado se desbloquea por que ya le mandaron algo

                    Encontrado.bloqueado = true;// se bloquea el proceso hasta que el otro le de recibir en la interfaz
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "El Proceso Emisor está bloqueado 1");
                    return;
                }

            } else {
                ArrayList<Mensaje> tmp=SingletonConfiguracion.mailboxGeneral.colaMensajes;
                //cae en direccionamiento indirecto
                // dijimos que statico y dinamico para send es nada más enviarlo a la variable mailboxGeneral
                // a nivel de interfaz lo que podemos hacer es bloquear el combobox2 de receptor en send y en receive se bloquea el de emisor
                   System.out.println("Entro a Direccionamiento Indirecto");
                if (Encontrado.bloqueado == false) {
                    Encontrado.enviados.colaMensajes.add(sms);
                    tmp.add(sms);
                    DesbloquearProcesoMailbox();
                    

                    Encontrado.bloqueado = true;// se bloquea el proceso hasta que el otro le de recibir en la interfaz
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "El Proceso Emisor está bloqueado 2 ");
                    return;
                }
            }
        } else {
            System.out.println("Entro a nonblocking");
            if (SingletonConfiguracion.direccionamientoDirecto == true) {
                System.out.println("Entro a direccionamiento directo");
                if (Encontrado.bloqueado == false) {
                    Encontrado.enviados.colaMensajes.add(sms);
                    Encontrado2.cola.colaMensajes.add(sms);
                    Encontrado2.bloqueado = false;// esto es nuevo, esto es porque si el proceso esta bloqueado se desbloquea por que ya le mandaron algo
                    return;
                } else {
                    JOptionPane.showMessageDialog(null, "El Proceso Emisor está bloqueado 3");
                    return;
                }
            } else {
                //cae en direccionamiento  indirecto
                if (Encontrado.bloqueado == false) {
                    System.out.println("Entro a direccionamiento inidrecto");
                    Encontrado.enviados.colaMensajes.add(sms);
                    SingletonConfiguracion.mailboxGeneral.colaMensajes.add(sms);
                    DesbloquearProcesoMailbox();
                } else {
                    JOptionPane.showMessageDialog(null, "El Proceso Emisor está bloqueado 4");
                    return;
                }
            }

        }

    }

    public static String receive(String id_Receptor, String id_emisor) { // id_Receptor (receptor en el send) es el id del proceso / hay que ver como hacemos esto a nivel de interfaz ( mandar el id_receptor)
        //Esto devuelve String       
        // cuando no es direccionamiento directo esto va null(id_emisor)
        String respuesta;
        Proceso Encontrado = EncontrarProceso(id_Receptor);
        Proceso Encontrado2 = EncontrarProceso(id_emisor);
        if (SingletonConfiguracion.FIFO == false) {
            ordenarPorPrioridad();

        }
        //posible esqueleto
        //aqui lo ordena 
        // saca el sms 
        if (SingletonConfiguracion.recieveBlocking == true) {
            //Direccionamiento directo e indirecto
            if (SingletonConfiguracion.direccionamientoDirecto == true) {

                if (SingletonConfiguracion.directoRecieveExplicito == true) {// dice de quien lo quiere recibir
                    //String Receptor= Encontrado.getIdentificadorProceso();
                    System.out.println("Tiene que entrar aqui");

                    //hace directo receive explicito
                    if (Encontrado.bloqueado == true) {
                        JOptionPane.showMessageDialog(null, "El Proceso Receptor está bloqueado 1");
                        return null;

                    } else {
                        for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                            if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {
                                if (Encontrado.cola.colaMensajes.get(i).identificadorEmisor.equals(id_emisor)) {
                                    respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                                    Encontrado.cola.colaMensajes.get(i).Recibido = true;
                                    Encontrado2.bloqueado = false;// esto es nuevo esto es porque nunca desbloqueamos al proceso emisor
                                    return respuesta;
                                }

                            }

                        }
                        Encontrado.bloqueado = true;// se bloquea porque estamos en blocking y nunca encontró un sms en la cola que no estuviera recibido
                        JOptionPane.showMessageDialog(null, "El Proceso Receptor se bloqueo ");
                        return null;

                    }
                } else {

                    if (Encontrado.bloqueado == true) {
                        JOptionPane.showMessageDialog(null, "El Proceso receptor está bloqueado ");
                        return null;

                    } else {
                        //hace directo receive implicito
                        for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                            if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {

                                respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                                Encontrado.cola.colaMensajes.get(i).Recibido = true;
                                Encontrado2.bloqueado = false;// esto es nuevo 

                                JOptionPane.showConfirmDialog(null, "El sms que recibí fue de " + Encontrado.cola.colaMensajes.get(i).identificadorEmisor);
                                return respuesta;
                            }

                        }
                        Encontrado.bloqueado = true;// se bloquea porque estamos en blocking y nunca encontró un sms en la cola que no estuviera recibido
                        JOptionPane.showMessageDialog(null, "El Proceso Receptor se bloqueo " + Encontrado.identificadorProceso);
                        return null;
                    }
                }

            } else {
                //cae en direccionamiento indirecto
                if (SingletonConfiguracion.indirectoEstatico == true) {//Solo un proceso va a recibir el sms , a nivel de interfaz solo uno puede estarlo
                    //cae en indirecto estatico
                    /*if (Encontrado.bloqueado == true) {
                        JOptionPane.showMessageDialog(null, "El Proceso Receptor está bloqueado ");
                        return null;
                    } else {*/
                        Mensaje smsTemporal = null;//
                        for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                            if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false && SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).identificadorEmisor!=Encontrado.identificadorProceso) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                                smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                                SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                                Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                                Encontrado_temp.bloqueado = false;
                                //Encontrado2.bloqueado = false;
                                break;
                            }

                        }

                        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                            if (SingletonConfiguracion.ListaDeProcesos.get(i).suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                                if (smsTemporal != null) {
                                    SingletonConfiguracion.ListaDeProcesos.get(i).cola.colaMensajes.add(smsTemporal);
                                    Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                    // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                   
                                    respuesta = smsTemporal.Contenido;
                                    return respuesta;
                                } else {
                                    Encontrado.bloqueado = true; // si el mailbox general no tiene ningún sms no recibido entonces se bloquea el encontrado
                                    SingletonConfiguracion.ListaDeProcesosBloqueados.add(Encontrado);// esto es porque en indirecto tengo que tener una lista aparte de bloqueados para deslboquear al primero 
                                    JOptionPane.showMessageDialog(null, "El Proceso Receptor se bloqueo ");
                                    return null;

                                }
                            }
                        }

                    //}
                } else {
                    //cae en indirecto dinamico
                    /*if (Encontrado.bloqueado == true) {
                        JOptionPane.showMessageDialog(null, "El Proceso Receptor está bloqueado ");
                        return null;
                    } else {*/
                        Mensaje smsTemporal = null;//
                        for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                            if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false && SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).identificadorEmisor!=Encontrado.identificadorProceso) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                                smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                                SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                                Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                                Encontrado_temp.bloqueado = false;
                                //Encontrado2.bloqueado = false;
                                break;
                            }

                        }

                        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                            if (Encontrado.suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                                if (smsTemporal != null) {
                                    Encontrado.cola.colaMensajes.add(smsTemporal);
                                    Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                    // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                  
                                    respuesta = smsTemporal.Contenido;
                                    return respuesta;
                                } else {
                                    Encontrado.bloqueado = true;// si el mailbox general no tiene ningún sms no recibido entonces se bloquea el encontrado
                                   SingletonConfiguracion.ListaDeProcesosBloqueados.add(Encontrado);// esto es porque en indirecto tengo que tener una lista aparte de bloqueados para deslboquear al primero 
                                    JOptionPane.showMessageDialog(null, "El Proceso Receptor se bloqueo ");
                                    return null;

                                }
                            }
                        }

                    }

                //}
            }
        } else if (SingletonConfiguracion.recieveNonBlocking == true) {
            //Direccionamiento directo e indirecto
            if (SingletonConfiguracion.direccionamientoDirecto == true) {

                if (SingletonConfiguracion.directoRecieveExplicito == true) {
                    //hace directo receive explicito

                    for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                        if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {
                            if (Encontrado.cola.colaMensajes.get(i).identificadorEmisor.equals(id_emisor)) {
                                respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                                Encontrado.cola.colaMensajes.get(i).Recibido = true;
                                Encontrado2.bloqueado = false;// esto es nuevo esto es porque nunca desbloqueamos al proceso emisor
                                return respuesta;
                            }

                        }

                    }

                } else {
                    //hace directo receive implicito
                    System.out.println("estoy en implicito nonblocking");
                    //hace directo receive implicito
                    for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                        if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {

                            respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                            Encontrado.cola.colaMensajes.get(i).Recibido = true;
                            Encontrado2.bloqueado = false;// esto es nuevo 
                            JOptionPane.showConfirmDialog(null, "El sms que recibí fue de " + Encontrado.cola.colaMensajes.get(i).identificadorEmisor);
                            System.out.println("nonblockin probando");
                            System.out.println(respuesta);
                            return respuesta;
                        }

                    }

                }

            } else {
                //cae en direccionamiento indirecto
                if (SingletonConfiguracion.indirectoEstatico == true) {
                    //cae en indirecto estatico

                    Mensaje smsTemporal = null;//
                    for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                        if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                            smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                            SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                            Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                            Encontrado_temp.bloqueado = false;
                            break;
                        }

                    }

                    for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                        if (SingletonConfiguracion.ListaDeProcesos.get(i).suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                            if (smsTemporal != null) {
                                SingletonConfiguracion.ListaDeProcesos.get(i).cola.colaMensajes.add(smsTemporal);
                                Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                respuesta = smsTemporal.Contenido;
                                return respuesta;
                            }
                        }
                    }

                } else {
                    //cae en indirecto dinamico

                    Mensaje smsTemporal = null;//
                    for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                        if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                            smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                            SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                            Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                            Encontrado_temp.bloqueado = false;
                            break;
                        }

                    }

                    for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                        if (Encontrado.suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                            if (smsTemporal != null) {
                                Encontrado.cola.colaMensajes.add(smsTemporal);
                                Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                respuesta = smsTemporal.Contenido;
                                return respuesta;
                            }
                        }
                    }

                }
            }
        } //Aqui cae cuando está en prueba de llegada
        else {
            JOptionPane.showMessageDialog(null, "Estamos en prueba de LLegada!");
            if (SingletonConfiguracion.direccionamientoDirecto == true) {

                if (SingletonConfiguracion.directoRecieveExplicito == true) {
                    //hace directo receive explicito

                    for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                        if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {
                            if (Encontrado.cola.colaMensajes.get(i).identificadorEmisor.equals(id_emisor)) {
                                respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                                Encontrado.cola.colaMensajes.get(i).Recibido = true;
                                Encontrado2.bloqueado = false;// esto es nuevo esto es porque nunca desbloqueamos al proceso emisor
                                return respuesta;
                            }

                        }

                    }

                } else {
                    //hace directo receive implicito
                    for (int i = 0; i < Encontrado.cola.colaMensajes.size(); i++) {
                        if (Encontrado.cola.colaMensajes.get(i).Recibido == false) {

                            respuesta = Encontrado.cola.colaMensajes.get(i).Contenido;
                            Encontrado.cola.colaMensajes.get(i).Recibido = true;
                            Encontrado2.bloqueado = false;// esto es nuevo esto es porque nunca desbloqueamos al proceso emisor
                            JOptionPane.showConfirmDialog(null, "El sms que recibí fue de " + Encontrado.cola.colaMensajes.get(i).identificadorEmisor);
                            return respuesta;
                        }

                    }

                }

            } else {
                //cae en direccionamiento indirecto
                if (SingletonConfiguracion.indirectoEstatico == true) {
                    //cae en indirecto estatico

                    Mensaje smsTemporal = null;//
                    for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                        if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                            smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                            SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                            Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                            Encontrado_temp.bloqueado = false;
                            break;
                        }

                    }

                    for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                        if (SingletonConfiguracion.ListaDeProcesos.get(i).suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                            if (smsTemporal != null) {
                                SingletonConfiguracion.ListaDeProcesos.get(i).cola.colaMensajes.add(smsTemporal);
                                Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                respuesta = smsTemporal.Contenido;
                                return respuesta;
                            }
                        }
                    }
                } else {
                    //cae en indirecto dinamico

                    Mensaje smsTemporal = null;//
                    for (int a = 0; a < SingletonConfiguracion.mailboxGeneral.colaMensajes.size(); a++) {//encontrar el primer sms no recibido del mailbox general
                        if (SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido == false) {// si no tiene ningun recibido false entonces smsTemporal se va a quedar null
                            smsTemporal = SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a);
                            SingletonConfiguracion.mailboxGeneral.colaMensajes.get(a).Recibido = true;
                            Proceso Encontrado_temp = EncontrarProceso(smsTemporal.identificadorEmisor);// el sms que estamos extrayendo del mailbox general tiene que tener el emisor a huevo entonces de ahí identificamos al proceso
                            Encontrado_temp.bloqueado = false;
                            break;
                        }

                    }

                    for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
                        if (Encontrado.suscrito == true) {//este codigo funciona para dinamico y estatico,PERO a nivl de interfaz solo vamos a tener uno suscrito
                            if (smsTemporal != null) {
                                Encontrado.cola.colaMensajes.add(smsTemporal);
                                Encontrado.cola.colaMensajes.get(Encontrado.cola.colaMensajes.size() - 1).Recibido = true;//como smstemporal se pone en la ultima posicion y es el sms que se va a 
                                // devolver entonces tenemos que ir a la lista de mensajes recibidos y marcar el sms como recibido porque lo vamos a devolver
                                respuesta = smsTemporal.Contenido;
                                return respuesta;
                            }
                        }
                    }
                }
            }

        }

        System.out.println("Llegó al final entonces no se metió en nada");
        return null;

    }

    public static Proceso EncontrarProceso(String id) {
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
            Proceso p=SingletonConfiguracion.ListaDeProcesos.get(i);
            if (p.getIdentificadorProceso().equals(id)) {
                return SingletonConfiguracion.ListaDeProcesos.get(i);
            }

        }
        return null;
    }

    public static void ordenarPorPrioridad() {//Aqui en teoria 
        /* Proceso Encontrado= EncontrarProceso(id_receptor);*/
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
            ArrayList<Mensaje> recibidos = SingletonConfiguracion.ListaDeProcesos.get(i).cola.colaMensajes;
            Collections.sort(recibidos, new SortPrioridad());
        }
        Collections.sort(SingletonConfiguracion.mailboxGeneral.colaMensajes, new SortPrioridad());

    }

    public static void Subscribe(String id_proceso) {
        Proceso p = EncontrarProceso(id_proceso);
        for (int i = 0; i < SingletonConfiguracion.ListaDeProcesos.size(); i++) {
            SingletonConfiguracion.ListaDeProcesos.get(i).suscrito = false;
        }
        p.suscrito = true;
        //p.bloqueado=false;//esto es porque si uno bloquea al receptor al no tener nada en estatico o nada en el mailbox genral
        // sabemos que el proceso que pongamos al subscribirse va a recibir un sms y por eso lo desbloqueamos
    }
    
    public static void DesbloquearProcesoMailbox(){//Desbloquea al primer proceso que encuentra en la lista de procesos bloqueados que solo se usan en indirecto block
        for(int i=0;i<SingletonConfiguracion.ListaDeProcesosBloqueados.size();i++){
            if(SingletonConfiguracion.ListaDeProcesosBloqueados.get(i).bloqueado==true){
                SingletonConfiguracion.ListaDeProcesosBloqueados.get(i).bloqueado=false;
                JOptionPane.showMessageDialog(null,"El proceso "+SingletonConfiguracion.ListaDeProcesosBloqueados.get(i).identificadorProceso+" ha sido desbloqueado por ser el siguiente a desbloquear");
                return;
            }
        }
    }

}
