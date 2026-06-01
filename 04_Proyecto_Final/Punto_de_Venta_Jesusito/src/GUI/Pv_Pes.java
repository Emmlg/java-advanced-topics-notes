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
package GUI;

import Modelo.AddTime;
import Modelo.Consultas;
import Modelo.GenerarPDF;
import Modelo.TableAction;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;





/**
 *
 * @author lopez
 */
public class Pv_Pes extends javax.swing.JInternalFrame {


    
    //variables

    final int cantidadP = 2;//columnas de las tablas
    final int precioP = 3;// columnas de las tablas
    final int subtotal = 8;//tabla de pedidos
    
    Consultas querying= new Consultas();
    TableAction tbQuerying = new TableAction();
    AddTime queryingTime =new AddTime();
    
    String provedorSelected ="";
    
    // VARIABLES DE PRUEBAS
    final int idEmpleado = 6;
    final String Efectivo= "efe";
    final String Debito= "deb";
    final String Credito= "crd";
    int idPedidoyfolio=0;
 
    public Pv_Pes() {
        
        
        initComponents();
        starDataConeccion();      
        
    }
    
    
// metodos de inicio
    
    public void starDataConeccion(){
    
         hintJtexfield(); //placeholder
         llenarJcomboBox(); 
         queryingTime.setDate(out_date_lb);
         
         getidProvedorYfolio();
         out_folioPed_lb.setText(Generarfolio(idPedidoyfolio));    
    
    }
    
    public void llenarJcomboBox(){
    
  String sqlProvedor = "SELECT concat(apePaterno, ' ', ' ', nombre,' con ID ',idPersona) as nombreC from persona where cvTipoPersona = 'ProTip'";
  String sqlfrpagos = "SELECT  dsFormPago from cformpago";
        
      querying.llenarJcombox(proveedor_jcbx, sqlProvedor);//proveedores
      querying.llenarJcombox(frPagos_jcbx,sqlfrpagos);//frpagos
      
    }
    
    
       public void hintJtexfield(){
       
         TextPrompt placeholder = new TextPrompt("Buscar los productos aquí", input_BarraBuscar_txf);
         placeholder = new TextPrompt("Historial de pedidos ", input_buscarPedido_txf);
       
   
   }
       
  public String Generarfolio(int idprovedoryfolio){
  
      String folio="";
      
      if(idprovedoryfolio>0 &&idprovedoryfolio<10){
      
          folio = "Folio-000"+idprovedoryfolio;
      }
      if(idprovedoryfolio>=10 &&idprovedoryfolio<100){
      
          folio = "Folio-00"+idprovedoryfolio;      
      }
      if(idprovedoryfolio>=100 &&idprovedoryfolio<1000){
          
          folio = "Folio-0"+idprovedoryfolio;
      }
  
  return folio;
  }  
  
  public void getidProvedorYfolio(){
  
         String sql="select idPedido from pedidos order by idPedido desc limit 1;";
         idPedidoyfolio = querying.verficarIdPedidoYfolio(sql);  
         idPedidoyfolio ++;
  
  }

       // METODOS DE EJECUCION
    
 
    public boolean isNumeric(String descrip){
    
        try {
            Integer.parseInt(descrip);
            return true;
        } catch (Exception e) {
            return false;
        }
    
    }
    
   public  boolean justLetter(String cadena) {
       
        for (int i = 0; i < cadena.length(); i++) {
            if (!Character.isLetter(cadena.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public void searchProduct(String provedor){
   
    String wordsearch = input_BarraBuscar_txf.getText(); 
    String sql ="";
    
    int indice = provedor.lastIndexOf(" ") + 1; // encuentra el índice del último espacio a
       
    int idProvedor =Integer.valueOf(provedor.substring(indice)) ; // extrae la subcadena
   //  System.out.println("pproveddor:"+provedor+" idprovedor "+idProvedor);

     if(isNumeric(wordsearch)){//id
  
      sql ="SELECT idProductos, descripcionP, cantidadP, precioC, marcaP, marcaCoche, modeloCoche, anioCoche"
          + " FROM persona,productos WHERE idProductos LIKE '"+wordsearch+"%' AND idPersona = "+idProvedor+" AND idPersona = idProveedor";
      

     }else
         if(justLetter(wordsearch)){//descripcion    
            
          sql = "SELECT idProductos,descripcionP, cantidadP, precioC, marcaP, marcaCoche, modeloCoche, anioCoche"
              + " FROM productos, persona WHERE descripcionP LIKE '"+wordsearch+"%' AND idPersona = "+idProvedor+" AND idPersona = idProveedor";
             System.out.println(sql);       
              
         }
         else{// numeroPartes
             
       sql ="SELECT idProductos, descripcionP, cantidadP, precioC, marcaP, marcaCoche, modeloCoche, anioCoche"
           + " FROM persona,productos WHERE nPartesP LIKE '"+wordsearch+"%' AND idPersona = "+idProvedor+" AND idPersona = idProveedor";
             

         }
        
    int columnCount = listPrdctAdd_tb.getColumnCount();
    tbQuerying.tbActn_delAllRows(listPrdctAdd_tb);//limpia la tabla
    querying.llenarTabla(listPrdctAdd_tb, columnCount, sql); //llena la tabla ,7
   
   }
   

   
   public void quitarLista(){
     
 
        int rowselected = listPedidoShow_tb.getSelectedRow();
        
        if( rowselected >= 0){            
        
        tbQuerying.tbActn_delRow(listPedidoShow_tb, rowselected);
        // actualizar total a pagar
        subYtotalAPagar();
        
        } 
   
       
   }
   
   public void subYtotalAPagar(){
   
        int colindexTotal = listPedidoShow_tb.getColumnCount()- 1; // columna de total
        //tbQuerying.tbActn_CalsubYTotalPago(listPedidoShow_tb,out_subTotal_lb,out_totalpago_lb,colindexTotal);
   
   }
   

   
   

 public boolean itemsRepetidos(int  rowselected){
 
     
    String idP = listPrdctAdd_tb.getValueAt(rowselected,0).toString(); //valores del id tb 1
   
     for (int i = 0; i < listPedidoShow_tb.getRowCount(); i++) {
         String cdbarras = listPedidoShow_tb.getValueAt(i,0).toString();
        // System.out.println("..."+cdbarras);
         
            if(cdbarras.equals(idP)){
                
                tbQuerying.tbActn_IncrementarCantPro(listPedidoShow_tb, i,cantidadP,precioP); // posicion 2 y 3 de cantidad y precio
                return true;
            }
     }
 return false;
 }
   

 
    public boolean itemSameProvedor(){
   
    int rowCount = listPedidoShow_tb.getRowCount();
    Object provedor = proveedor_jcbx.getSelectedItem();
          
        if(provedorSelected.equals(provedor) && rowCount >= 0) {            
           
            return true;
        }
    
    return false;
    }
 
    public String cvFormpagoValidate(Object cvfrpago){
    String cvdepago ="";
    
    if(cvfrpago.equals("Efectivo")){
        cvdepago = Efectivo;
    }
    if(cvfrpago.equals("Credito")){
        cvdepago = Credito;
    }
    if(cvfrpago.equals("Debito")){
    cvdepago = Debito;
    }
    
        return cvdepago;
    }
    

 
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgBluePedidos_jp = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        frPagos_jcbx = new javax.swing.JComboBox<>();
        out_subTotal_lb = new javax.swing.JLabel();
        iva_lb = new javax.swing.JLabel();
        out_totalpago_lb = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        out_user_lb = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        out_date_lb = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        out_folioPed_lb = new javax.swing.JLabel();
        hacerPedido_bttn = new javax.swing.JButton();
        cancelarPedido_bttn = new javax.swing.JButton();
        input_buscarPedido_txf = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        search_lb1 = new javax.swing.JLabel();
        actualizarPedido_bttn = new javax.swing.JButton();
        bgWhitePedidos_jp = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listPedidoShow_tb = new javax.swing.JTable();
        delete_bttn = new javax.swing.JButton();
        vaciarLs_Bttn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listPrdctAdd_tb = new javax.swing.JTable();
        input_BarraBuscar_txf = new javax.swing.JTextField();
        addProduct_bttn = new javax.swing.JButton();
        search_lb = new javax.swing.JLabel();
        newPedidos_bttn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        proveedor_jcbx = new javax.swing.JComboBox<>();
        addProvedor_lb = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        bgBluePedidos_jp.setBackground(new java.awt.Color(37, 60, 89));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Usuario :");

        jPanel5.setBackground(new java.awt.Color(37, 60, 89));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2), "Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 22), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sub Total ");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Iva ");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Total a pagar ");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Forma de Pago ");

        frPagos_jcbx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        frPagos_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- seleccionar --" }));
        frPagos_jcbx.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                frPagos_jcbxPropertyChange(evt);
            }
        });

        out_subTotal_lb.setBackground(new java.awt.Color(37, 60, 89));
        out_subTotal_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        out_subTotal_lb.setForeground(new java.awt.Color(255, 255, 255));
        out_subTotal_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_subTotal_lb.setText("$ 0.0");
        out_subTotal_lb.setOpaque(true);

        iva_lb.setBackground(new java.awt.Color(37, 60, 89));
        iva_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        iva_lb.setForeground(new java.awt.Color(255, 255, 255));
        iva_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iva_lb.setText("16 %");
        iva_lb.setOpaque(true);

        out_totalpago_lb.setBackground(new java.awt.Color(37, 60, 89));
        out_totalpago_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        out_totalpago_lb.setForeground(new java.awt.Color(255, 255, 255));
        out_totalpago_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_totalpago_lb.setText("$ 0.0");
        out_totalpago_lb.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(frPagos_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap(27, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(out_subTotal_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(iva_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(out_totalpago_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frPagos_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(41, 41, 41)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(out_subTotal_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(iva_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(19, 19, 19)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(out_totalpago_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        out_user_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_user_lb.setForeground(new java.awt.Color(255, 255, 255));
        out_user_lb.setText("Usuario 001");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fecha :");

        out_date_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_date_lb.setForeground(new java.awt.Color(255, 255, 255));
        out_date_lb.setText("02/05/2023");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Folio de Pedido :");

        out_folioPed_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_folioPed_lb.setForeground(new java.awt.Color(255, 255, 255));
        out_folioPed_lb.setText("pedido001");

        hacerPedido_bttn.setBackground(new java.awt.Color(255, 255, 255));
        hacerPedido_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        hacerPedido_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pedido.png"))); // NOI18N
        hacerPedido_bttn.setText("Realizar Pedido");
        hacerPedido_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerPedido_bttnActionPerformed(evt);
            }
        });

        cancelarPedido_bttn.setBackground(new java.awt.Color(255, 255, 255));
        cancelarPedido_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        cancelarPedido_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelPedido.png"))); // NOI18N
        cancelarPedido_bttn.setText("Cancelar Pedido");

        input_buscarPedido_txf.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        input_buscarPedido_txf.setForeground(new java.awt.Color(37, 60, 89));

        jLabel4.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Buscar pedido :");

        search_lb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/research.png"))); // NOI18N
        search_lb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_lb1MouseClicked(evt);
            }
        });

        actualizarPedido_bttn.setBackground(new java.awt.Color(255, 255, 255));
        actualizarPedido_bttn.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        actualizarPedido_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/updatePedido.png"))); // NOI18N
        actualizarPedido_bttn.setText("Actualizar Pedido");
        actualizarPedido_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        actualizarPedido_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarPedido_bttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bgBluePedidos_jpLayout = new javax.swing.GroupLayout(bgBluePedidos_jp);
        bgBluePedidos_jp.setLayout(bgBluePedidos_jpLayout);
        bgBluePedidos_jpLayout.setHorizontalGroup(
            bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(jLabel7)
                        .addGap(40, 40, 40)
                        .addComponent(out_date_lb))
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel9)
                        .addGap(40, 40, 40)
                        .addComponent(out_folioPed_lb))
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel2)
                        .addGap(38, 38, 38)
                        .addComponent(out_user_lb))
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addGap(20, 20, 20)
                        .addComponent(input_buscarPedido_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(search_lb1))
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                                .addComponent(hacerPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelarPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bgBluePedidos_jpLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(actualizarPedido_bttn)
                        .addGap(261, 261, 261)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        bgBluePedidos_jpLayout.setVerticalGroup(
            bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(out_date_lb))
                .addGap(14, 14, 14)
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(out_folioPed_lb))
                .addGap(14, 14, 14)
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(out_user_lb))
                .addGap(35, 35, 35)
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgBluePedidos_jpLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel4))
                    .addComponent(input_buscarPedido_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(search_lb1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(actualizarPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bgBluePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hacerPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelarPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        bgWhitePedidos_jp.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), " Lista de Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        listPedidoShow_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        listPedidoShow_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Barras", "Descripcion", "Cantidad", "Precio", "Marca", "Marca del Coche", "Modelo Cochel", "Año del coche", "subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listPedidoShow_tb.setRowHeight(30);
        listPedidoShow_tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listPedidoShow_tbKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(listPedidoShow_tb);
        if (listPedidoShow_tb.getColumnModel().getColumnCount() > 0) {
            listPedidoShow_tb.getColumnModel().getColumn(0).setMinWidth(140);
            listPedidoShow_tb.getColumnModel().getColumn(0).setMaxWidth(200);
            listPedidoShow_tb.getColumnModel().getColumn(1).setMinWidth(200);
            listPedidoShow_tb.getColumnModel().getColumn(2).setMinWidth(80);
            listPedidoShow_tb.getColumnModel().getColumn(3).setMinWidth(80);
            listPedidoShow_tb.getColumnModel().getColumn(4).setMinWidth(100);
            listPedidoShow_tb.getColumnModel().getColumn(5).setMinWidth(100);
            listPedidoShow_tb.getColumnModel().getColumn(6).setMinWidth(100);
            listPedidoShow_tb.getColumnModel().getColumn(7).setMinWidth(130);
            listPedidoShow_tb.getColumnModel().getColumn(7).setMaxWidth(150);
            listPedidoShow_tb.getColumnModel().getColumn(8).setMinWidth(120);
        }

        delete_bttn.setBackground(new java.awt.Color(255, 255, 255));
        delete_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        delete_bttn.setForeground(new java.awt.Color(37, 60, 89));
        delete_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitItem.png"))); // NOI18N
        delete_bttn.setText("Quitar de la lista");
        delete_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        delete_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_bttnActionPerformed(evt);
            }
        });

        vaciarLs_Bttn.setBackground(new java.awt.Color(255, 255, 255));
        vaciarLs_Bttn.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        vaciarLs_Bttn.setForeground(new java.awt.Color(37, 60, 89));
        vaciarLs_Bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/clean.png"))); // NOI18N
        vaciarLs_Bttn.setText("Vaciar lista");
        vaciarLs_Bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        vaciarLs_Bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaciarLs_BttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(vaciarLs_Bttn)
                        .addGap(18, 18, 18)
                        .addComponent(delete_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_bttn)
                    .addComponent(vaciarLs_Bttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), " Lista de Productos ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        listPrdctAdd_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        listPrdctAdd_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Barras", "Descripcion", "Cantidad", "Precio", "Marca", "Marca  Coche", "Modelo Coche", "Año del coche"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listPrdctAdd_tb.setAlignmentX(1.0F);
        listPrdctAdd_tb.setAlignmentY(1.0F);
        listPrdctAdd_tb.setName(""); // NOI18N
        listPrdctAdd_tb.setRowHeight(30);
        jScrollPane2.setViewportView(listPrdctAdd_tb);
        if (listPrdctAdd_tb.getColumnModel().getColumnCount() > 0) {
            listPrdctAdd_tb.getColumnModel().getColumn(0).setMinWidth(140);
            listPrdctAdd_tb.getColumnModel().getColumn(0).setMaxWidth(200);
            listPrdctAdd_tb.getColumnModel().getColumn(1).setMinWidth(220);
            listPrdctAdd_tb.getColumnModel().getColumn(2).setMinWidth(100);
            listPrdctAdd_tb.getColumnModel().getColumn(2).setMaxWidth(120);
            listPrdctAdd_tb.getColumnModel().getColumn(3).setMinWidth(110);
            listPrdctAdd_tb.getColumnModel().getColumn(4).setMinWidth(100);
            listPrdctAdd_tb.getColumnModel().getColumn(5).setMinWidth(100);
            listPrdctAdd_tb.getColumnModel().getColumn(6).setMinWidth(100);
            listPrdctAdd_tb.getColumnModel().getColumn(7).setMinWidth(100);
            listPrdctAdd_tb.getColumnModel().getColumn(7).setMaxWidth(120);
        }

        input_BarraBuscar_txf.setBackground(new java.awt.Color(37, 60, 89));
        input_BarraBuscar_txf.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        input_BarraBuscar_txf.setForeground(new java.awt.Color(255, 255, 255));
        input_BarraBuscar_txf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_BarraBuscar_txf.setCaretColor(new java.awt.Color(255, 255, 255));
        input_BarraBuscar_txf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_BarraBuscar_txfKeyReleased(evt);
            }
        });

        addProduct_bttn.setBackground(new java.awt.Color(255, 255, 255));
        addProduct_bttn.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        addProduct_bttn.setForeground(new java.awt.Color(37, 60, 89));
        addProduct_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/addItem.png"))); // NOI18N
        addProduct_bttn.setText("Agregar a lista");
        addProduct_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        addProduct_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct_bttnActionPerformed(evt);
            }
        });

        search_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/research.png"))); // NOI18N

        newPedidos_bttn.setBackground(new java.awt.Color(255, 255, 255));
        newPedidos_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        newPedidos_bttn.setForeground(new java.awt.Color(37, 60, 89));
        newPedidos_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/newProduct.png"))); // NOI18N
        newPedidos_bttn.setText("Nuevo Producto");
        newPedidos_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        newPedidos_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPedidos_bttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(input_BarraBuscar_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(search_lb)
                .addGap(18, 18, 18)
                .addComponent(addProduct_bttn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newPedidos_bttn)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(search_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(input_BarraBuscar_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(addProduct_bttn)
                            .addComponent(newPedidos_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(37, 60, 89));
        jLabel6.setText("Proveedor");

        proveedor_jcbx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        proveedor_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---  seleccionar ---" }));
        proveedor_jcbx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proveedor_jcbxItemStateChanged(evt);
            }
        });

        addProvedor_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/addUser.png"))); // NOI18N
        addProvedor_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProvedor_lbMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout bgWhitePedidos_jpLayout = new javax.swing.GroupLayout(bgWhitePedidos_jp);
        bgWhitePedidos_jp.setLayout(bgWhitePedidos_jpLayout);
        bgWhitePedidos_jpLayout.setHorizontalGroup(
            bgWhitePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgWhitePedidos_jpLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bgWhitePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(bgWhitePedidos_jpLayout.createSequentialGroup()
                        .addGap(419, 419, 419)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proveedor_jcbx, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(30, 30, 30)
                        .addComponent(addProvedor_lb)
                        .addGap(21, 21, 21))))
        );
        bgWhitePedidos_jpLayout.setVerticalGroup(
            bgWhitePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgWhitePedidos_jpLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(bgWhitePedidos_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(proveedor_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProvedor_lb)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bgWhitePedidos_jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bgBluePedidos_jp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgBluePedidos_jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bgWhitePedidos_jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void proveedor_jcbxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proveedor_jcbxItemStateChanged

        
        tbQuerying.tbActn_delAllRows(listPrdctAdd_tb);// eliminamos filas de las tablas

        String provedor = proveedor_jcbx.getSelectedItem().toString();        
        
        
        if (listPedidoShow_tb.getRowCount() <= 0){
              
            provedorSelected = provedor; // agragamos el provedor mientras no haya producto
            
        }
        
      //  System.out.println("jcbx Provedor : "+provedor);
      
      

        int indice = provedor.lastIndexOf(" ") + 1; // encuentra el índice del último espacio a
        int idProvedor =Integer.valueOf(provedor.substring(indice)) ; // extrae la subcadena
       
        System.out.println(idProvedor);
        
        String sql  = "SELECT idProductos,descripcionP, cantidadP, precioC, marcaP, marcaCoche, modeloCoche, anioCoche"
                    + " FROM productos, persona WHERE idPersona = "+idProvedor+" AND idProveedor = idPersona ";
        
        
               
    
    int columnCount = listPrdctAdd_tb.getColumnCount();
    querying.llenarTabla(listPrdctAdd_tb, columnCount,sql);
       
    }//GEN-LAST:event_proveedor_jcbxItemStateChanged

    private void input_BarraBuscar_txfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_BarraBuscar_txfKeyReleased

        String provedor = proveedor_jcbx.getSelectedItem().toString();
        System.out.println("-----provedor "+provedor);
        searchProduct(provedor);  
        
    }//GEN-LAST:event_input_BarraBuscar_txfKeyReleased

    private void delete_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_bttnActionPerformed
       
        quitarLista();
        
    }//GEN-LAST:event_delete_bttnActionPerformed

    private void addProduct_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct_bttnActionPerformed

        // AGREGAR DATOS A LA TABLA PEDIDOS
        int rowIndex = listPrdctAdd_tb.getSelectedRow(); // Número de fila que deseas obtener
       
        if(rowIndex >= 0 && itemSameProvedor()){
            
            if(! itemsRepetidos(rowIndex) )
                tbQuerying.tbActn_Pedido_addRow(listPrdctAdd_tb, listPedidoShow_tb, rowIndex);
            
               
        
             }else{
               JOptionPane.showMessageDialog(Pv_Pes.this, " Solo puede hacer pedidos de un Proveedor ");          
             
             }      
            // CALCULAR PAGO TOTAL
            subYtotalAPagar();        
        
       
    }//GEN-LAST:event_addProduct_bttnActionPerformed

    private void search_lb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_lb1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_search_lb1MouseClicked

    private void vaciarLs_BttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaciarLs_BttnActionPerformed
       

        tbQuerying.tbActn_delAllRows(listPedidoShow_tb);
        subYtotalAPagar(); 
        
    }//GEN-LAST:event_vaciarLs_BttnActionPerformed

    private void actualizarPedido_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarPedido_bttnActionPerformed
       
    
   
        
    }//GEN-LAST:event_actualizarPedido_bttnActionPerformed

    private void listPedidoShow_tbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listPedidoShow_tbKeyReleased

               char tecla = evt.getKeyChar();
       
               if(tecla == KeyEvent.VK_ENTER){
                   
                  int rowselected = listPedidoShow_tb.getSelectedRow();
                  
                  // ene sta ocacion 2 y 3 es el cantidad y precio index
                  
                  tbQuerying.tbActn_CalTotalProducto(listPedidoShow_tb, rowselected,cantidadP,precioP);               
                  subYtotalAPagar(); 
               
               }

    }//GEN-LAST:event_listPedidoShow_tbKeyReleased

    private void newPedidos_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPedidos_bttnActionPerformed

        Pv_Pedidos jinternal_pedido = new Pv_Pedidos();
         AddJinternal addwjf = new AddJinternal();
         addwjf.addJinternals(jinternal_pedido);        
        
        Pv_Inventario inventarioW = new Pv_Inventario();        
        Pv_MenuGeneral.bgMenuGeneral_jp.add(inventarioW);
        inventarioW.toFront();//visualizar enfrente
        inventarioW.setVisible(true);
        
        
        
    }//GEN-LAST:event_newPedidos_bttnActionPerformed

    private void addProvedor_lbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProvedor_lbMouseClicked

        Pv_DatosPersona dtPersonaW = new Pv_DatosPersona();        
        Pv_MenuGeneral.bgMenuGeneral_jp.add(dtPersonaW);
        dtPersonaW.toFront();//visualizar enfrente
        dtPersonaW.setVisible(true);



    }//GEN-LAST:event_addProvedor_lbMouseClicked

    private void hacerPedido_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hacerPedido_bttnActionPerformed

        
        if(frPagos_jcbx.getSelectedIndex()>0 && proveedor_jcbx.getSelectedIndex()>0){
        
            String sqldtallepedido = 
           "insert into detallesPedido (idPedido, idProducto, cantProducto, precioC, subTotalPedido)\n" +
           "VALUES(?,?,?,?,?)";
         
            int filas =listPedidoShow_tb.getRowCount() ,columnas=5; // 5 de idProducto, cantProducto, precioC,subTotalPedido
            Object[][]  detallesPedido = new Object[filas][columnas];

            System.out.println("\thacer pedidos");
            for (int i = 0; i < detallesPedido.length; i++) {
            
               /**
                    0,2,3,8 posiciones de  idProducto, cantProducto, precioC,subTotalPedido en la tabla
                   tambien podemos obtener los indices con getname("nombre de la columna a buscar pero no es necesario por el momento")
               */
                detallesPedido[i][0] =idPedidoyfolio; //idPedido
                detallesPedido[i][1] =listPedidoShow_tb.getValueAt(i, 0);//idProducto
                detallesPedido[i][2] =listPedidoShow_tb.getValueAt(i, 2); //cantProducto
                detallesPedido[i][3] =listPedidoShow_tb.getValueAt(i, 3);//precioC
                detallesPedido[i][4] =listPedidoShow_tb.getValueAt(i, 8);//subTotalPedido
            
            }
        


     /*   for (Object[] fila : detallesPedido) {
            for (Object elemento : fila) {
                // Realiza alguna operación con el elemento
                System.out.print(elemento +" ");
            }
            System.out.println("");
        }*/

            String sqlPedido = 
            "insert into pedidos (idEmpleado, cvFormPago, idProveedor, folioP, IVA, totalPedido, fechaPedido)\n" +
            "values(?,?,?,?,?,?,?)";

            int indice = provedorSelected.lastIndexOf(" ") + 1; // encuentra el índice del último espacio a
            int idProvedor =Integer.valueOf(provedorSelected.substring(indice)) ; // extrae la subcadena

            double[] CalIvaYTotalPago =tbQuerying.tbActn_CalIvaYTotalPago(listPedidoShow_tb,8);

            Object[] pedido = new Object[7];

            pedido[0] = idEmpleado;//idEmpleado
            pedido[1] = cvFormpagoValidate(frPagos_jcbx.getSelectedItem());//cvFormPago
            pedido[2] = idProvedor;//idProveedor
            pedido[3] = Generarfolio(idPedidoyfolio);//folioP
            pedido[4] = CalIvaYTotalPago[1]; //IVA
            pedido[5] = CalIvaYTotalPago[0]; //totalPedido
            pedido[6] = queryingTime.date("yyyy-MM-dd");//fechaPedido

          /*
                System.out.println("*******2consulta");

            for( Object fila : pedido){
                System.out.print(fila+" ");
            }*/

     //TRANSACCIONES
                
                //ENVIO DE PEDIDOS EN DB
          
          //*  querying.mkpedido(detallesPedido,sqldtallepedido);//productos
       //*    querying.mkpedido2(pedido,sqlPedido);// personas
             
               //GENERAMOS PDF
        /*       columnas =listPedidoShow_tb.getColumnCount();
               Object[][] pdfpedidos = new Object[filas][columnas];
               for (int i = 0; i < pdfpedidos.length; i++) {
                   for (int j = 0; j < pdfpedidos[i].length; j++) {
                       pdfpedidos[i][j] = listPedidoShow_tb.getValueAt(i,j);
                    }
                }
             GenerarPDF pdf = new GenerarPDF();
            pdf.pedidoPDF(pdfpedidos,out_folioPed_lb.getText());*/
             
             JOptionPane.showMessageDialog(Pv_Pes.this, " Datos guardado con exito ");
             
             //limpiamos tablas 
            tbQuerying.tbActn_delAllRows(listPedidoShow_tb);
            subYtotalAPagar(); 

            //generamos el nuevo folio
             getidProvedorYfolio();
             out_folioPed_lb.setText(Generarfolio(idPedidoyfolio));
        
        }else{
        JOptionPane.showMessageDialog(Pv_Pes.this, " Seleccione los datos correctamente ");  
        
        }
        

        
        
        
    }//GEN-LAST:event_hacerPedido_bttnActionPerformed

    private void frPagos_jcbxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_frPagos_jcbxPropertyChange
               
    }//GEN-LAST:event_frPagos_jcbxPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarPedido_bttn;
    private javax.swing.JButton addProduct_bttn;
    private javax.swing.JLabel addProvedor_lb;
    private javax.swing.JPanel bgBluePedidos_jp;
    private javax.swing.JPanel bgWhitePedidos_jp;
    private javax.swing.JButton cancelarPedido_bttn;
    private javax.swing.JButton delete_bttn;
    public javax.swing.JComboBox<String> frPagos_jcbx;
    private javax.swing.JButton hacerPedido_bttn;
    private javax.swing.JTextField input_BarraBuscar_txf;
    private javax.swing.JTextField input_buscarPedido_txf;
    private javax.swing.JLabel iva_lb;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JTable listPedidoShow_tb;
    public javax.swing.JTable listPrdctAdd_tb;
    private javax.swing.JButton newPedidos_bttn;
    private javax.swing.JLabel out_date_lb;
    private javax.swing.JLabel out_folioPed_lb;
    private javax.swing.JLabel out_subTotal_lb;
    private javax.swing.JLabel out_totalpago_lb;
    private javax.swing.JLabel out_user_lb;
    public javax.swing.JComboBox<String> proveedor_jcbx;
    private javax.swing.JLabel search_lb;
    private javax.swing.JLabel search_lb1;
    private javax.swing.JButton vaciarLs_Bttn;
    // End of variables declaration//GEN-END:variables
}
