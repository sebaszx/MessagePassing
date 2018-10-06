/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package messagepassing;

import java.util.Comparator;

/**
 *
 * @author Sebas
 */
public class SortPrioridad implements Comparator<Mensaje>{

   
    public int compare(Mensaje t, Mensaje t1) {
       return t.prioridad - t1.prioridad;
    }
    
}
