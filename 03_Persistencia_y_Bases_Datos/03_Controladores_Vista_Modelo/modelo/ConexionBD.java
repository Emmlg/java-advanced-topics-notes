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
public class ConexionBD {
    
    // variables
    
    Connection miConexion = null;

    public ConexionBD() {
    
    
    }
    
    public Connection DameConexion(){
        
        
     try {
            // 1 CREAR CONEXION
            
            miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdalumnog2", "root", "admin");
            

        
        } catch (Exception e) {
            
            System.out.println("error de conexion ");
            e.printStackTrace();            
          
        }
    
     return miConexion;
    }
    
    
    
    
}
