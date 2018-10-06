/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

/**
 *
 * @author Saul
 */
public class Batch {

    private String identificadorEmisor;
    private String identificadorReceptor;
    private TipoContenido tipo;
    private int largo;/// si largo es 0 es que no hay tamaño fijo, pero si es diferente de 0 es de tamaño fijo
    //si es texto entonces n caracteres y si no son n megas.
    private String Contenido;// url o texto
    private int prioridad;// si es 0 entonces es FIFO si no si tiene prioridad
    private boolean Recibido;// dice si el sms en la cola recibidos fue recibido para desbloquear al proceso receptor, esto quiere decir 
    // que hay que buscar al proceso del IDEmisor y luego preguntar que si esta bloqueado entonces desbloqueelo al darle Receive en la interfaz
    private boolean Enviado; //enviado true es que va hacer receive

    public Batch(int prioridad, String identificadorEmisor, String identificadorReceptor, TipoContenido tipo, int largo, String Contenido, boolean recibido, boolean enviado) {
        this.identificadorEmisor = identificadorEmisor;
        this.identificadorReceptor = identificadorReceptor;
        this.tipo = tipo;
        this.largo = largo;
        this.Contenido = Contenido;
        this.prioridad = prioridad;
        this.Recibido = recibido;
        this.Enviado = enviado;
    }

    public Batch(int prioridad, String identificadorEmisor, String identificadorReceptor, TipoContenido tipo, int largo, String Contenido, boolean recibido) {
        this.identificadorEmisor = identificadorEmisor;
        this.identificadorReceptor = identificadorReceptor;
        this.tipo = tipo;
        this.largo = largo;
        this.Contenido = Contenido;
        this.prioridad = prioridad;
        this.Recibido = recibido;
    }

    public Batch() {
    }

    public Batch(String identificadorReceptor, String identificadorEmisor) {
        this.identificadorEmisor = identificadorEmisor;
        this.identificadorReceptor = identificadorReceptor;
    }

    /**
     * @return the identificadorEmisor
     */
    public String getIdentificadorEmisor() {
        return identificadorEmisor;
    }

    /**
     * @param identificadorEmisor the identificadorEmisor to set
     */
    public void setIdentificadorEmisor(String identificadorEmisor) {
        this.identificadorEmisor = identificadorEmisor;
    }

    /**
     * @return the identificadorReceptor
     */
    public String getIdentificadorReceptor() {
        return identificadorReceptor;
    }

    /**
     * @param identificadorReceptor the identificadorReceptor to set
     */
    public void setIdentificadorReceptor(String identificadorReceptor) {
        this.identificadorReceptor = identificadorReceptor;
    }

    /**
     * @return the tipo
     */
    public TipoContenido getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoContenido tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the largo
     */
    public int getLargo() {
        return largo;
    }

    /**
     * @param largo the largo to set
     */
    public void setLargo(int largo) {
        this.largo = largo;
    }

    /**
     * @return the Contenido
     */
    public String getContenido() {
        return Contenido;
    }

    /**
     * @param Contenido the Contenido to set
     */
    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }

    /**
     * @return the prioridad
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * @param prioridad the prioridad to set
     */
    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * @return the Recibido
     */
    public boolean isRecibido() {
        return Recibido;
    }

    /**
     * @param Recibido the Recibido to set
     */
    public void setRecibido(boolean Recibido) {
        this.Recibido = Recibido;
    }

    /**
     * @return the Enviado
     */
    public boolean isEnviado() {
        return Enviado;
    }

    /**
     * @param Enviado the Enviado to set
     */
    public void setEnviado(boolean Enviado) {
        this.Enviado = Enviado;
    }

    public static void leerBatch() throws IOException {
        
        JFileChooser Chooser = new JFileChooser();
        Chooser.showOpenDialog(null);
        File file = Chooser.getSelectedFile();
        //File file = new File("C:\\Users\\Saul\\Desktop\\prueba1.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        ArrayList mensajesBatch = new ArrayList();
        String[] temp;
        ArrayList<Batch> listaBatch = new ArrayList<Batch>();
        String[] separados;

        String st;
        while ((st = br.readLine()) != null) { //Aqui se lee el txt
            //System.out.println(st);
            separados = st.split(";");

          

            String emisor = separados[0];
            String receptor = separados[1];
            String tipoContenido = separados[2];
            TipoContenido tipoContenidoA = TipoContenido.valueOf(tipoContenido);
            String largo = separados[3];
            int largoInt = Integer.parseInt(largo);
            String contenido = separados[4];
            String prioridad = separados[5];
            int prioridadInt = Integer.parseInt(prioridad);
            String recibido = separados[6];
            boolean recibidoBoolean = Boolean.parseBoolean(recibido);
            String enviado = separados[7];
            boolean enviadoBoolean = Boolean.parseBoolean(enviado);

            Batch temporal = new Batch(prioridadInt, emisor, receptor, tipoContenidoA, largoInt, contenido, recibidoBoolean, enviadoBoolean); //Hacer casting
            listaBatch.add(temporal);

        }
            //System.out.println(listaBatch);

        for (int i = 0; i < listaBatch.size(); i++) {
            //System.out.println(listaBatch.get(i).identificadorReceptor+" "+listaBatch.get(i).identificadorEmisor);

            if (listaBatch.get(i).Enviado == true) {
                Proceso.receive(listaBatch.get(i).getIdentificadorReceptor(), listaBatch.get(i).getIdentificadorEmisor());
            } else {

                Mensaje smsTemp = new Mensaje(listaBatch.get(i).getPrioridad(), listaBatch.get(i).getIdentificadorEmisor(), listaBatch.get(i).getIdentificadorReceptor(), listaBatch.get(i).getTipo(), listaBatch.get(i).getLargo(), listaBatch.get(i).getContenido(), listaBatch.get(i).isRecibido());
                Proceso.send(smsTemp);

            }
        }

    }
}
