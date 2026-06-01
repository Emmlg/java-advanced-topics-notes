/**
 * Tema: Punto de Venta Jesusito
 * Conceptos: src
 * Autor: Emmlg
 */




package Modelo;

import java.sql.Connection;
import java.sql.*;

/**
 *
 * @author Emmanuel lopez
 */
public class DBConexion {
    
        // variables
    
    Connection miConexion = null;

    public DBConexion() {
    
       try {
            // 1 CREAR CONEXION
            
            miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/basejesusito_2", "root", "admin");
            System.out.println("Conexion exitosa");
        
        } catch (Exception e) {
            
            System.out.println("Error de conexion ");
            e.printStackTrace();            
          
        }
    
    }
    
    public Connection DameConexion(){
            
     return miConexion;
     
    }
    
    
    
    
}
