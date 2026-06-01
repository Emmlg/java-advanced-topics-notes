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
package modelo;

import java.sql.*;

/**
 *
 * @author lopez
 */
public class CargarJcomboBox {
    
    ConexionBD miConexion ;
    public ResultSet rs;

    public CargarJcomboBox() {
    
        miConexion = new ConexionBD();
    
    }
    
    public String ejecutarConsultas(){
    
        AlumnosG2 tbAlumnosG2 = null;
        Connection accesoBBDD =  miConexion.DameConexion();
        
        try {
            
            Statement secciones = accesoBBDD .createStatement();
            
            rs = secciones.executeQuery("SELECT Nombre FROM alumnosg2");
            
           
          //  while (rs.next()) {
               
                
                tbAlumnosG2 = new AlumnosG2();
                
                tbAlumnosG2.setNombre(rs.getString(1));
                
                
            //    return tbAlumnosG2.getNombre();                
                
           // }
            rs.close();
            
        } catch (Exception e) {
            e.getMessage();
        }
    
        return tbAlumnosG2.getNombre();
    }
    
    
    
}
