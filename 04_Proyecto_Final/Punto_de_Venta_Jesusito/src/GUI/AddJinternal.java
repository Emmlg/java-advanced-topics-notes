/**
 * Tema: Punto de Venta Jesusito
 * Conceptos: src
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import GUI.Pv_MenuGeneral;
import static GUI.Pv_MenuGeneral.bgMenuGeneral_jp;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;


/**
 *
 * @author lopez
 */
public class AddJinternal  {

    
    
    public AddJinternal() {
    
    }

    
    
    
        public void addJinternals(JInternalFrame wFrame){
        
       removebgJpanel();
       
       bgMenuGeneral_jp.add(wFrame);

 
        try {
            
            wFrame.setMaximum(true);
         
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Pv_MenuGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
        
     wFrame.show();
    
    }
    
    public void removebgJpanel(){
    
      bgMenuGeneral_jp.removeAll();
      bgMenuGeneral_jp.repaint();
    
    
    }
    

}