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
package Modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import java.util.Date;

/**
 *
 * @author lopez
 */
public class AddTime {


    
    public AddTime() {
        

    
    }
    

    
    public void setDate(JLabel etiqueta){
            
        LocalDate fechact = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");        
        etiqueta.setText(fechact.format(formato));
        
    }
    
        public String date(String format) {
        
        LocalDate fechact = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(format);        
       return ""+fechact.format(formato);
    
    }
        
    public String datef(Date date){
    
        
        Long d = date.getTime();
        java.sql.Date fecha = new java.sql.Date(d);
               
       return ""+fecha;
   
    }    
    

    
}
