/**
 * Tema: 02 CRUD MySQL
 * Conceptos: CRUD MySQL
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionDB;

import java.sql.*;

/**
 *
 * @author lopez
 */
public class Conexion {
    
    Connection miConexion;
    
    public Conexion(){
        
        try {
          
            // 1 CREAR CONEXION
            
         //   class.Class.forName()
            
             miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/registro", "root", "admin");
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        }

          
}
    
    
    public Connection getConnection(){
    
    return miConexion;
    }
    
}
