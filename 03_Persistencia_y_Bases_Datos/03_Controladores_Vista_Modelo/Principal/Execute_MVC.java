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
package Principal;

import javax.swing.JFrame;
import vista.Marco_Aplicacion;

/**
 *
 * @author lopez
 */
public class Execute_MVC {
    public static void main(String[] args) {
     
        Marco_Aplicacion mimarco= new Marco_Aplicacion();
        
        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mimarco.setVisible(true);
        
        
    }    
}
