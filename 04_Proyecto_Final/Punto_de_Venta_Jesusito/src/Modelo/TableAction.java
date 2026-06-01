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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author lopez
 */
public class TableAction {

    public TableAction() {
    
    }
    
    
    
    DefaultTableModel tbModel;
    
    public void tbActn_delRow(JTable tbName, int rowSelected){
    
 
     tbModel = (DefaultTableModel) tbName.getModel();
     tbModel.removeRow(rowSelected);
     tbModel = null;
 
    
    }
    
    public void tbActn_delAllRows(JTable tbName){
    
        tbModel = (DefaultTableModel) tbName.getModel();        
        tbModel.setRowCount(0); 
        tbModel = null;
    
    
    }
    
    public void tbActn_CalsubYTotalPago(JTable tbName,JLabel subtotal,JLabel iva,JLabel total,int columnTotPrdct){
        
       double totalpago = 0;
       
       for (int i = 0; i < tbName.getRowCount(); i++) {
           
            totalpago += Double.valueOf(tbName.getValueAt(i,columnTotPrdct).toString()) ;// fila,columna total
            
        //   System.out.println("--subtotal pago : "+totalpago);
       }
       
   subtotal.setText("$ "+totalpago);
   
   iva.setText("$ "+(totalpago*.16));
   
   total.setText("$ "+(totalpago*1.16));
        
    
    }
    
    
    
    public void tbActn_CalTotalProducto(JTable tbName ,int rowSelected ,int colcantidad,int colPrecio){
    
        int coltotal = tbName.getColumnCount() - 1;
        Object totPago;
        
        int cantidad =  Integer.valueOf(tbName.getValueAt(rowSelected,colcantidad).toString());
        double precio = Double.valueOf(tbName.getValueAt(rowSelected,colPrecio).toString());
        
         totPago = cantidad*precio;
    
         tbName.setValueAt(totPago, rowSelected,coltotal);
    
    }
    
    
    public void tbActn_Pedido_addRow(JTable tbGetItem,JTable tbSetItem ,int rowSele){
        
         tbModel = (DefaultTableModel) tbSetItem.getModel();//tabla a llenar
         Object[] items = new Object[tbGetItem.getColumnCount()+1];
         double total = 1;
         
       // obtener los datos de la otra tabla
        for (int i = 0; i < tbGetItem.getColumnCount(); i++) {            
            
             if( i < 3 || i == 4)
                 items [i] = tbGetItem.getValueAt(rowSele,i);
             
             if(i== 3)
                items [i] = 1;
             
                  
             if(i ==4 ){ // col de 3 y 4 por cant y precio                 

              total *= Double.valueOf(tbGetItem.getValueAt(rowSele,i).toString());     
               //  System.out.println("checar total : "+total);
             
             }
                
        }
        
        // calcular el total producto y lo agregamos a la tabla        
        
      items[items.length - 1] =  total;// -1 porque queda en el ultimo index          
        
        //pasar los datos a otro jtable ,pedidos        
        tbModel.addRow(items);                 
        tbModel = null ;
        
    }
    
    public void tbActn_IncrementarCantPro(JTable tbName ,int rowSelected ,int colcantidad,int colPrecio){
        
        int coltotal = tbName.getColumnCount() - 1;
        Object totPago;
        
        int cantidad =  Integer.valueOf(tbName.getValueAt(rowSelected,colcantidad).toString()) + 1;
        double precio = Double.valueOf(tbName.getValueAt(rowSelected,colPrecio).toString());
        
         totPago = cantidad*precio;
    
         tbName.setValueAt(cantidad, rowSelected, colcantidad);
         tbName.setValueAt(totPago, rowSelected,coltotal);


    }
    
    
  public double[] tbActn_CalIvaYTotalPago(JTable tbName,int columnsubTPrdct){
          
       double[] ivatotal=new double[3];
       
       for (int i = 0; i < tbName.getRowCount(); i++) {
           
            ivatotal[0] += Double.valueOf(tbName.getValueAt(i,columnsubTPrdct).toString()) ;// fila,columna subtotal
            
           
       }
       ivatotal[1] = ivatotal[0]*.16;//iva
       ivatotal[2] = ivatotal[0]*1.16; //total

  return ivatotal; 
    
    }
    
    
    
    
}//fin de clase
