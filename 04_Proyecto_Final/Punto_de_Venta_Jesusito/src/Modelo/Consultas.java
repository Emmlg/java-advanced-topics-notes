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

import com.toedter.calendar.JDateChooser;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lopez
 */
public class Consultas {
    
      DBConexion miConexion  = new DBConexion();;
      Connection con;
      Statement st;
      ResultSet rs;
      PreparedStatement ps;
      DefaultTableModel tbmodelo;
      
      
   public void llenarJcombox(JComboBox<String> jcbxName , String sql){
     
       try {
                      
            con = miConexion.DameConexion();
            st=con.createStatement();
            rs=st.executeQuery(sql);


                while (rs.next()) {    

                   jcbxName.addItem(rs.getString(1));

                //  System.out.println("cbx : "+rs.getString(1));

                }
           rs.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
       
   
   }
      
   public void llenarTabla(JTable tbName ,int columnCount , String sql){
   
     try {
            
            con = miConexion.DameConexion();
            st=con.createStatement();
            rs=st.executeQuery(sql);
            
            
            Object[] items = new Object[columnCount];
            
            tbmodelo = (DefaultTableModel)tbName.getModel();
            
            while (rs.next()) {    
               //System.out.println("dentro de llenado de tablas");
                for (int i = 0; i < columnCount; i++) {
                    
                    items[i] = rs.getString(i+1);
//                    System.out.print(" productos : "+rs.getString(i+1)+" index "+(i+1));
//                    System.out.println("\n "+items[i]);
                    
                }
              /*  
                producto[0] = rs.getString("idProductos");
                producto[1] = rs.getString("descripcionP");                
                producto[2] = "1";
                producto[3] = rs.getString("precioC");
                producto[4] = "0";*/
                tbmodelo.addRow(items);
                
            }
            tbName.setModel(tbmodelo);
            
            
        } catch (Exception e) {
            //e.printStackTrace();
           // JOptionPane.showMessageDialog(null, "el producto no existe");
           System.out.println("\nError En llenado de tabla");
           e.printStackTrace();
        }
       
       
       
   
   }
   
   public int getIdProvedor(String provedor){
      int idProvedor = 0;
      String sql = "SELECT idPersona FROM persona,detalleproveedor "
        +"WHERE concat(apePaterno, ' ', apeMaterno, ' ', nombre, ' ', empresa, ' ', puesto) = '"+provedor+"' AND idPersona = idProveedor";
      try {
                      
            con = miConexion.DameConexion();
            st=con.createStatement();
            rs=st.executeQuery(sql);


                while (rs.next()) {    

                  idProvedor = Integer.valueOf(rs.getString(1));

                }
            rs.close();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    //System.out.println("iddeporvedddor "+idProvedor);
   return idProvedor;
   }
      
      
  public void insertIntoDB(Object[][] values,String sqldtllePed){
   
       try {
           
           con = miConexion.DameConexion();
           ps =con.prepareStatement(sqldtllePed);
 
           // GUARDAMOS LOS DATOS DE LOS PRODUCTOS
           for (int i = 0; i <values.length; i++) {
               for (int j = 0; j <values[i].length; j++) {
                   
                   String prueba =values[i][j].toString();//no es necesario puedo cambiar el tipo de dato a enviar
                    ps.setString(j+1 ,prueba);
                 //   System.out.println("------detalles pedidos\n");
                  //  System.out.print((j+1)+"  "+values[i][j].toString()+" ");
                   
               }
//               ps.executeUpdate();

                 if(ps.executeUpdate()>0){
                    System.out.println("datos guardados con exito*");
                    }else{
                    System.out.println("Error al guardar los datos* ");
                    }
               System.out.println("");
           }
           

           
           
        } catch (Exception e) {
           System.out.println("\nno guarmaos los datos en db de detalles de pedido");
           e.printStackTrace();
        }
  
       
  
  } 
  
  public void insertIntoDB(Object[] pedido ,String sqlpedidos){
  
                 //GUARDAMOS LOS DATOS DE PEDIDOS
          
          try {
              
               con = miConexion.DameConexion();
              ps = con.prepareStatement(sqlpedidos);
            for (int i = 0; i < pedido.length; i++) {
               
               ps.setString(i+1,pedido[i].toString());
              // System.out.println("--pedidos");
              // System.out.print( pedido[i].toString()+" ");
               
           }
            
         
            if(ps.executeUpdate()>0){
               System.out.println("datos 2 guardados con exito**");
           }else
               System.out.println("Error al guardar los datos** ");
            
              
              
          } catch (SQLException ex) {
              
              ex.printStackTrace();
              System.out.println("no guardo la db pedido");
          }
           

  
  
  }
      
      
public int verficarIdPedidoYfolio(String sqlIdProvedor){
    
    int idprovedor = 0;
   try {
           
           con = miConexion.DameConexion();
            st=con.createStatement();
            rs=st.executeQuery(sqlIdProvedor);
            while (rs.next()) {           
           idprovedor = Integer.valueOf(rs.getString(1));
         //  System.out.println("idprovedor : "+rs.getString(1)); 
            }
           
             
        } catch (Exception e) {
           System.out.println("\nIdprovedor en null");
          // e.printStackTrace();
           idprovedor = 0;
        }
    return idprovedor;
}
   

public void detallesPedido(String sql,JDateChooser fechEntrega,JComboBox<String> frPago,JTextField anticipo,JComboBox<String> estadoEntreg){

   try {
           
            con = miConexion.DameConexion();
            st=con.createStatement();
            rs=st.executeQuery(sql);
            
            while (rs.next()) {           
          
          fechEntrega.setDate(rs.getDate(1));
          frPago.setSelectedItem(rs.getString(2));        
          anticipo.setText(rs.getString(3));
          estadoEntreg.setSelectedItem(rs.getString(4));
                
         
           }
           
             
        } catch (Exception e) {
           System.out.println("error!!!");
          // e.printStackTrace();
          
        }


}

    public String  getIdPedido(String folio){
        
        String sql ="SELECT  idPedido from pedidos where folioP = '"+folio+"'";
        try {

                con = miConexion.DameConexion();
                st=con.createStatement();
                rs=st.executeQuery(sql);


                    while (rs.next()) {    

                        folio = rs.getString(1);

                    }
                    rs.close();


      } catch (Exception e) {
            System.out.println("no se obtuvo el idPedido");
      }
        return folio;
    }




    
}//finde clases consultas
