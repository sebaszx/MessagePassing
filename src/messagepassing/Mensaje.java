/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

/**
 *
 * @author Sebas
 */
public class Mensaje {

    String identificadorEmisor;
    String identificadorReceptor;
    TipoContenido tipo;
    int largo;/// si largo es 0 es que no hay tamaño fijo, pero si es diferente de 0 es de tamaño fijo
    //si es texto entonces n caracteres y si no son n megas.
    String Contenido;// url o texto
    int prioridad;// si es 0 entonces es FIFO si no si tiene prioridad
    boolean Recibido;// dice si el sms en la cola recibidos fue recibido para desbloquear al proceso receptor, esto quiere decir 
    // que hay que buscar al proceso del IDEmisor y luego preguntar que si esta bloqueado entonces desbloqueelo al darle Receive en la interfaz

    public Mensaje(int prioridad, String identificadorEmisor, String identificadorReceptor, TipoContenido tipo, int largo, String Contenido, boolean recibido) {
        this.identificadorEmisor = identificadorEmisor;
        this.identificadorReceptor = identificadorReceptor;
        this.tipo = tipo;
        this.largo = largo;
        this.Contenido = Contenido;
        this.prioridad = prioridad;
        this.Recibido=recibido;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isRecibido() {
        return Recibido;
    }

    public void setRecibido(boolean Recibido) {
        this.Recibido = Recibido;
    }

    
    public String getIdentificadorEmisor() {
        return identificadorEmisor;
    }

    public void setIdentificadorEmisor(String identificadorEmisor) {
        this.identificadorEmisor = identificadorEmisor;
    }

    public String getIdentificadorReceptor() {
        return identificadorReceptor;
    }

    public void setIdentificadorReceptor(String identificadorReceptor) {
        this.identificadorReceptor = identificadorReceptor;
    }

    public TipoContenido getTipo() {
        return tipo;
    }

    public void setTipo(TipoContenido tipo) {
        this.tipo = tipo;
    }

    public int getLargo() {
        return largo;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }

}
