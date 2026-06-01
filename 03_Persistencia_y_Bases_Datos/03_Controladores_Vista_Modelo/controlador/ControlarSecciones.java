/**
 * Tema: 03 Controladores Vista Modelo
 * Conceptos: Controladores Vista Modelo
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.*;
import modelo.CargarJcomboBox;
import vista.Marco_Aplicacion;

/**
 *
 * @author lopez
 */
public class ControlarSecciones extends WindowAdapter{
    
    CargarJcomboBox obj = new CargarJcomboBox();
    
    private Marco_Aplicacion elmarco;

    public ControlarSecciones(Marco_Aplicacion objeto) {
    
        this.elmarco = objeto;
        
    
    }
    
    
    
    
    @Override
    public void windowOpened(WindowEvent e){
        
        obj.ejecutarConsultas();
        
        try {
            
            while (obj.rs.next()) {
                
                elmarco.secciones.addItem(obj.rs.getString(1));                
                
            }
            
            
        } catch (Exception e2) {
            e2.getMessage();
            
        }
    
    }
    
}
