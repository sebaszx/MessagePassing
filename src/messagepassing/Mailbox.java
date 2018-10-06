/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.util.ArrayList;

/**
 *
 * @author Sebas
 */
public class Mailbox {
    String identificador;// tiene que ser igual al identificador del proceso
    ArrayList<Mensaje> colaMensajes = new ArrayList<Mensaje>();

    public Mailbox() {
    }

    
    
   

    public Mailbox(String identificador) {
        this.identificador = identificador;
    }
    
    
     // metodo ordenarPrioridad

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public ArrayList<Mensaje> getColaMensajes() {
        return colaMensajes;
    }

    public void setColaMensajes(ArrayList<Mensaje> colaMensajes) {
        this.colaMensajes = colaMensajes;
    }
    
    
}
