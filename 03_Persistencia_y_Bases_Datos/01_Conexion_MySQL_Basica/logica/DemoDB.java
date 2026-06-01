/**
 * Tema: 01 Conexion MySQL Basica
 * Conceptos: Conexion MySQL Basica
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lopez
 */
public class DemoDB {
  
    private  static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "admin";
    private static final String url = "jdbc:mysql://localhost:3306/mydb";

    public DemoDB() {
    conn=null;
    
        try {
           //Class.forName(driver);
            conn=DriverManager.getConnection(url,user,password);
            if(conn != null){
                System.out.println("conexion establecida");
            }
            
            
        } catch (SQLException e) {
            System.out.println("error al conectar "+e);
        
        }         
        
    }
    
    
    
    
  
    public Connection getConnection(){//retorna la conexion
        
        return conn;
        }  
    public void desconnectar(){
     conn = null;
     if(conn ==null){
         System.out.println("conexion terminada .. ");
     
     }
    }
 
    
    
    
}//demo
