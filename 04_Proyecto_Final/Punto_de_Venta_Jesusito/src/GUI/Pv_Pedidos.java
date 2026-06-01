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
import Modelo.TableAction;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;

/**
 *
 * @author lopez
 */
public class Pv_Pedidos extends javax.swing.JInternalFrame {

    
    //variables

    final int cantidadP = 3;//columnas de las tablas
    final int precioP = 4;// columnas de las tablas
    final int subtotal = 5;//tabla de pedidos
    
        //historial
    final int folioindex = 3;
    
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
    
    final String edo_Entregado = "Ent";
    final String edo_camino = "Cam";
    final String edo_Retrasado = "Ret";
    final String edo_Cancelado = "Can";

    public Pv_Pedidos() {
        initComponents();
        starDataConeccion();      
        
    }

// metodos de inicio
    
    public void starDataConeccion(){
    
         hintJtexfield(); //placeholder
         llenarJcomboBox(); 
         queryingTime.setDate(out_dateP_lb);
         
         getidProvedorYfolio();
         out_folioPedP_lb.setText(Generarfolio(idPedidoyfolio));    
    
    }
    
    public void llenarJcomboBox(){
    
  String sqlProvedor = "SELECT  concat(apePaterno, ' ', apeMaterno, ' ', nombre, ' ',empresa, ' ', puesto )"
                     + "as dsProv from persona, detalleproveedor where cvTipoPersona = 'ProTip' and idPersona = idProveedor";
  String sqlfrpagos = "SELECT  dsFormPago from cformpago";
        
      querying.llenarJcombox(proveedorP_jcbx, sqlProvedor);//provedoresPedido
      querying.llenarJcombox(frPagosP_jcbx, sqlfrpagos);
     
      //Historial de pedidos
       querying.llenarJcombox(proveedorH_jcbx, sqlProvedor);//proveedores
       
      
      
    }
    
    
       public void hintJtexfield(){
       
           //pedidos
         TextPrompt placeholder = new TextPrompt("Buscar los productos aquí", input_BarraBuscarP_txf);
         placeholder = new TextPrompt("$ anticipo",input_AnticipoP_jtxf);
         
         //historial de pedidos
         placeholder = new TextPrompt("Busca por el folio eje. Folio-001", input_BuscarFolioH_txf);
         placeholder = new TextPrompt("agrega el nuevo anticipo", out_anticipoH_txf);
        
       
   
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
  
         String sql="SELECT idPedido from pedidos order by idPedido desc limit 1;";
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
   
    String wordsearch = input_BarraBuscarP_txf.getText(); 
    String typeid ="";
    

     if(isNumeric(wordsearch)){//id
  
         typeid = "idProductos";

     }else
         if(justLetter(wordsearch)){//descripcion    
                 
              typeid = "descripcionP";
         }
         else{// numeroPartes
             
             typeid ="nPartesP";

         }
     
     
String sql 
   = "SELECT idProductos, concat(descripcionP,'  ', marcaCoche, ' ', modeloCoche, ' ', anioCoche)"
   + " as infProducto, marcaP, cantidadP, precioC from productos, persona" 
   + " WHERE "+typeid +" LIKE '"+wordsearch+"%' AND idPersona = "+querying.getIdProvedor(provedor)+" AND idProveedor = idPersona";


    int columnCount = listPrdctAddP_tb.getColumnCount();
    tbQuerying.tbActn_delAllRows(listPrdctAddP_tb);//limpia la tabla
    querying.llenarTabla(listPrdctAddP_tb, columnCount, sql); //llena la tabla ,7
   
   }
   

   
   public void quitarLista(){
     
 
        int rowselected = listPedidoShowP_tb.getSelectedRow();
        
        if( rowselected >= 0){            
        
        tbQuerying.tbActn_delRow(listPedidoShowP_tb, rowselected);
        // actualizar total a pagar
        subYtotalAPagar();
        
        } 
    
   }
   
   public void cleanALL(){
   
   provedorSelected = "";  
   idPedidoyfolio = 0;
       
   out_subTotalP_lb.setText("$ 0.0");
   out_totalpagoP_lb.setText("$ 0.0");
   out_ivaP_lb.setText("$ 0.0");
   
   input_BarraBuscarP_txf.setText("");
   input_AnticipoP_jtxf.setText("");
   input_dateEntregaP_Jdate.setDate(null);
      
   tbQuerying.tbActn_delAllRows(listPrdctAddP_tb);
   tbQuerying.tbActn_delAllRows(listPedidoShowP_tb);
   
   frPagosP_jcbx.setSelectedIndex(0);
   proveedorP_jcbx.setSelectedIndex(0);
  

   
   }
   
   public void cancelActualizacion(){
   
      
       
       frPagoH_Jcbx.removeAllItems();
       edoEntrgaH_jcbx.removeAllItems();
       frPagoH_Jcbx.addItem("-- Forma Pago --");
       edoEntrgaH_jcbx.addItem("-- Estado Entrega --");
       
       
       input_FromDateH_JDate.setDate(null);
       input_ToDateH_JDate.setDate(null);
       out_FechEntregaH_Jdate.setDate(null);
       
       input_BuscarFolioH_txf.setText("");
       out_anticipoH_txf.setText("");
       out_subTotalH_lb.setText("$ 0.0");
       out_ivaH_lb.setText("$ 0.0");
       out_totalpagoH_lb.setText("$ 0.0");
       out_folioPedidoH_lb.setText("");
       
       
       tbQuerying.tbActn_delAllRows(lsPedidoH_tb);
       //tbQuerying.tbActn_delAllRows(lsHistorialH_tb);
        proveedorH_jcbx.setSelectedIndex(proveedorH_jcbx.getSelectedIndex());
       
   
   }
   
   public void subYtotalAPagar(){
   
        int colindexTotal = listPedidoShowP_tb.getColumnCount()- 1; // columna de total
        tbQuerying.tbActn_CalsubYTotalPago(listPedidoShowP_tb,out_subTotalP_lb,out_ivaP_lb,out_totalpagoP_lb,colindexTotal);
   
   }
   

   
   

 public boolean itemsRepetidos(int  rowselected){
 
     
    String idP = listPrdctAddP_tb.getValueAt(rowselected,0).toString(); //valores del id tb 1
   
     for (int i = 0; i < listPedidoShowP_tb.getRowCount(); i++) {
         String cdbarras = listPedidoShowP_tb.getValueAt(i,0).toString();
        // System.out.println("..."+cdbarras);
         
            if(cdbarras.equals(idP)){
                
                tbQuerying.tbActn_IncrementarCantPro(listPedidoShowP_tb, i,cantidadP,precioP); // posicion 2 y 3 de cantidad y precio
                return true;
            }
     }
 return false;
 }
   

 
    public boolean itemSameProvedor(){
   
    int rowCount = listPedidoShowP_tb.getRowCount();
    Object provedor = proveedorP_jcbx.getSelectedItem();
          
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
    
    public String cvEdoEntregaVal(Object cvEdo){
    String edo="";
    
    if(cvEdo.equals("Entregado"))
         edo = edo_Entregado;
     if (cvEdo.equals("EnCamino"))
         edo = edo_camino;
     if(cvEdo.equals("Retrasado"))
         edo = edo_Retrasado;
     if(cvEdo.equals("Cancelado"))
         edo = edo_Cancelado;
    
    
    
    return edo;
    }

    public void searchPedByFech(){
    
            if(input_FromDateH_JDate != null && input_ToDateH_JDate != null){
            
             tbQuerying.tbActn_delAllRows(lsHistorialH_tb);
             
            String fromdate = queryingTime.datef(input_FromDateH_JDate.getDate());
            String   toDate = queryingTime.datef(input_ToDateH_JDate.getDate());
           
            String sql =
            "select idEmpleado, cFormPago.dsFormPago,"
            + " concat(persona.nombre, ' ', persona.apePaterno, ' ',persona.apeMaterno, ' ', empresa, ' ', puesto)"
            + " as infProv, folioP, pedidos.subTotal, IVA, totalPedido, fechaPedido, fechaEntrega"
            + " FROM pedidos, persona, detalleproveedor, cFormPago"
            + " WHERE pedidos.fechaPedido between '"+fromdate+"' AND '"+toDate+"' AND pedidos.idProveedor = persona.idPersona"
            + " AND detalleproveedor.idProveedor = persona.idPersona AND pedidos.cvFormPago = cFormPago.cvFormPago";       
        
                int columnCount = lsHistorialH_tb.getColumnCount();
                querying.llenarTabla(lsHistorialH_tb, columnCount,sql);
               // System.out.println(sql);
               // System.out.println(fromdate+"\n"+toDate);
        
        }
          //JOptionPane.showMessageDialog(Pv_Pedidos.this, " Solo puede hacer pedidos de un Proveedor ");
    
    
    }
    
    public void disableCompontent(JPanel panel,boolean edocomponent){
        
        Component[] componentes = panel.getComponents(); // Obtener los componentes contenidos en el panel
        
        for (Component componente : componentes) {
            componente.setEnabled(edocomponent); // Desactivar cada componente
        
    }
    
    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        RealizrPedido_jp = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listPrdctAddP_tb = new javax.swing.JTable();
        input_BarraBuscarP_txf = new javax.swing.JTextField();
        addProductP_bttn = new javax.swing.JButton();
        search_lb3 = new javax.swing.JLabel();
        newPedidosP_bttn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listPedidoShowP_tb = new javax.swing.JTable();
        deleteP_bttn = new javax.swing.JButton();
        vaciarLsP_Bttn = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        frPagosP_jcbx = new javax.swing.JComboBox<>();
        out_subTotalP_lb = new javax.swing.JLabel();
        out_ivaP_lb = new javax.swing.JLabel();
        out_totalpagoP_lb = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        input_dateEntregaP_Jdate = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        input_AnticipoP_jtxf = new javax.swing.JTextField();
        hacerPedidoP_bttn = new javax.swing.JButton();
        cancelarPedidoP_bttn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        out_dateP_lb = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        out_userP_lb = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        out_folioPedP_lb = new javax.swing.JLabel();
        proveedorP_jcbx = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        addProvedor_lb1 = new javax.swing.JLabel();
        historialPedido_jp = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        out_userH_lb = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        out_dateH_lb = new javax.swing.JLabel();
        visualizarHistorial_jp = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lsHistorialH_tb = new javax.swing.JTable();
        input_BuscarFolioH_txf = new javax.swing.JTextField();
        search_lb = new javax.swing.JLabel();
        VerDetalleH_btnn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        input_FromDateH_JDate = new com.toedter.calendar.JDateChooser();
        input_ToDateH_JDate = new com.toedter.calendar.JDateChooser();
        proveedorH_jcbx = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lsPedidoH_tb = new javax.swing.JTable();
        actualizarPedido_bttn = new javax.swing.JButton();
        cancelarActualizacion_bttn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        out_subTotalH_lb = new javax.swing.JLabel();
        out_ivaH_lb = new javax.swing.JLabel();
        out_totalpagoH_lb = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        frPagoH_Jcbx = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        out_anticipoH_txf = new javax.swing.JTextField();
        out_FechEntregaH_Jdate = new com.toedter.calendar.JDateChooser();
        jLabel24 = new javax.swing.JLabel();
        edoEntrgaH_jcbx = new javax.swing.JComboBox<>();
        out_folioPedidoH_lb = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N

        RealizrPedido_jp.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), " Lista de Productos ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        listPrdctAddP_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        listPrdctAddP_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Barras", "Descripción del Producto", "Marca del Producto", "Cantidad Disponible", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listPrdctAddP_tb.setAlignmentX(1.0F);
        listPrdctAddP_tb.setAlignmentY(1.0F);
        listPrdctAddP_tb.setName(""); // NOI18N
        listPrdctAddP_tb.setRowHeight(30);
        jScrollPane4.setViewportView(listPrdctAddP_tb);
        if (listPrdctAddP_tb.getColumnModel().getColumnCount() > 0) {
            listPrdctAddP_tb.getColumnModel().getColumn(0).setMinWidth(120);
            listPrdctAddP_tb.getColumnModel().getColumn(1).setMinWidth(600);
            listPrdctAddP_tb.getColumnModel().getColumn(2).setMinWidth(120);
            listPrdctAddP_tb.getColumnModel().getColumn(3).setMinWidth(150);
            listPrdctAddP_tb.getColumnModel().getColumn(3).setMaxWidth(200);
            listPrdctAddP_tb.getColumnModel().getColumn(4).setMinWidth(100);
            listPrdctAddP_tb.getColumnModel().getColumn(4).setMaxWidth(200);
        }

        input_BarraBuscarP_txf.setBackground(new java.awt.Color(37, 60, 89));
        input_BarraBuscarP_txf.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        input_BarraBuscarP_txf.setForeground(new java.awt.Color(255, 255, 255));
        input_BarraBuscarP_txf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_BarraBuscarP_txf.setCaretColor(new java.awt.Color(255, 255, 255));
        input_BarraBuscarP_txf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_BarraBuscarP_txfKeyReleased(evt);
            }
        });

        addProductP_bttn.setBackground(new java.awt.Color(255, 255, 255));
        addProductP_bttn.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        addProductP_bttn.setForeground(new java.awt.Color(37, 60, 89));
        addProductP_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/addItem.png"))); // NOI18N
        addProductP_bttn.setText("Agregar a lista");
        addProductP_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        addProductP_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProductP_bttnActionPerformed(evt);
            }
        });

        search_lb3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/research.png"))); // NOI18N

        newPedidosP_bttn.setBackground(new java.awt.Color(255, 255, 255));
        newPedidosP_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        newPedidosP_bttn.setForeground(new java.awt.Color(37, 60, 89));
        newPedidosP_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/newProduct.png"))); // NOI18N
        newPedidosP_bttn.setText("Nuevo Producto");
        newPedidosP_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        newPedidosP_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPedidosP_bttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane4)
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(295, 295, 295)
                .addComponent(input_BarraBuscarP_txf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(search_lb3)
                .addGap(174, 174, 174)
                .addComponent(addProductP_bttn)
                .addGap(41, 41, 41)
                .addComponent(newPedidosP_bttn)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(search_lb3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(input_BarraBuscarP_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(newPedidosP_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addProductP_bttn)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), " Lista de Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        listPedidoShowP_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        listPedidoShowP_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Barras", "Descripción del producto", "Marca del Producto", "Cantidad a Pedir", "Precio a Pagar", "subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        listPedidoShowP_tb.setRowHeight(30);
        listPedidoShowP_tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                listPedidoShowP_tbKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(listPedidoShowP_tb);
        if (listPedidoShowP_tb.getColumnModel().getColumnCount() > 0) {
            listPedidoShowP_tb.getColumnModel().getColumn(0).setMinWidth(150);
            listPedidoShowP_tb.getColumnModel().getColumn(0).setMaxWidth(220);
            listPedidoShowP_tb.getColumnModel().getColumn(1).setMinWidth(300);
            listPedidoShowP_tb.getColumnModel().getColumn(2).setMinWidth(150);
            listPedidoShowP_tb.getColumnModel().getColumn(2).setMaxWidth(250);
            listPedidoShowP_tb.getColumnModel().getColumn(3).setMinWidth(80);
            listPedidoShowP_tb.getColumnModel().getColumn(3).setMaxWidth(150);
            listPedidoShowP_tb.getColumnModel().getColumn(4).setMinWidth(100);
            listPedidoShowP_tb.getColumnModel().getColumn(4).setMaxWidth(200);
            listPedidoShowP_tb.getColumnModel().getColumn(5).setMinWidth(100);
            listPedidoShowP_tb.getColumnModel().getColumn(5).setMaxWidth(200);
        }

        deleteP_bttn.setBackground(new java.awt.Color(255, 255, 255));
        deleteP_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        deleteP_bttn.setForeground(new java.awt.Color(37, 60, 89));
        deleteP_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/quitItem.png"))); // NOI18N
        deleteP_bttn.setText("Quitar de la lista");
        deleteP_bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        deleteP_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteP_bttnActionPerformed(evt);
            }
        });

        vaciarLsP_Bttn.setBackground(new java.awt.Color(255, 255, 255));
        vaciarLsP_Bttn.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        vaciarLsP_Bttn.setForeground(new java.awt.Color(37, 60, 89));
        vaciarLsP_Bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/clean.png"))); // NOI18N
        vaciarLsP_Bttn.setText("Vaciar lista");
        vaciarLsP_Bttn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        vaciarLsP_Bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vaciarLsP_BttnActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), "Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 22), new java.awt.Color(37, 60, 89))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(37, 60, 89));

        jLabel14.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(37, 60, 89));
        jLabel14.setText("Sub Total ");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(37, 60, 89));
        jLabel15.setText("Iva ");

        jLabel17.setBackground(new java.awt.Color(204, 0, 102));
        jLabel17.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(37, 60, 89));
        jLabel17.setText("Total a pagar ");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(37, 60, 89));
        jLabel18.setText("Forma de Pago ");

        frPagosP_jcbx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        frPagosP_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- seleccionar --" }));
        frPagosP_jcbx.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                frPagosP_jcbxPropertyChange(evt);
            }
        });

        out_subTotalP_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_subTotalP_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        out_subTotalP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_subTotalP_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_subTotalP_lb.setText("$ 0.0");
        out_subTotalP_lb.setOpaque(true);

        out_ivaP_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_ivaP_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        out_ivaP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_ivaP_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_ivaP_lb.setText("$ 0.0");
        out_ivaP_lb.setOpaque(true);

        out_totalpagoP_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_totalpagoP_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        out_totalpagoP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_totalpagoP_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_totalpagoP_lb.setText("$ 0.0");
        out_totalpagoP_lb.setOpaque(true);

        jSeparator2.setBackground(new java.awt.Color(37, 60, 89));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        jLabel1.setBackground(new java.awt.Color(37, 60, 89));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(37, 60, 89));
        jLabel1.setText("Fecha Entrega");

        input_dateEntregaP_Jdate.setBackground(new java.awt.Color(37, 60, 89));
        input_dateEntregaP_Jdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));
        input_dateEntregaP_Jdate.setForeground(new java.awt.Color(37, 60, 89));

        jLabel20.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(37, 60, 89));
        jLabel20.setText("Anticipo");

        input_AnticipoP_jtxf.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        input_AnticipoP_jtxf.setForeground(new java.awt.Color(37, 60, 89));
        input_AnticipoP_jtxf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));

        hacerPedidoP_bttn.setBackground(new java.awt.Color(255, 255, 255));
        hacerPedidoP_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        hacerPedidoP_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pedido.png"))); // NOI18N
        hacerPedidoP_bttn.setText("Realizar Pedido");
        hacerPedidoP_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hacerPedidoP_bttnActionPerformed(evt);
            }
        });

        cancelarPedidoP_bttn.setBackground(new java.awt.Color(255, 255, 255));
        cancelarPedidoP_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        cancelarPedidoP_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelPedido.png"))); // NOI18N
        cancelarPedidoP_bttn.setText("Cancelar Pedido");
        cancelarPedidoP_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarPedidoP_bttnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(hacerPedidoP_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelarPedidoP_bttn)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(228, 228, 228))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel17)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(out_totalpagoP_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(98, 98, 98)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(out_ivaP_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(input_AnticipoP_jtxf, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(out_subTotalP_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel18))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(frPagosP_jcbx, 0, 200, Short.MAX_VALUE)
                            .addComponent(input_dateEntregaP_Jdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(input_dateEntregaP_Jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(frPagosP_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(input_AnticipoP_jtxf, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14)
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(out_subTotalP_lb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(out_ivaP_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(out_totalpagoP_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hacerPedidoP_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelarPedidoP_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(vaciarLsP_Bttn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteP_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteP_bttn)
                    .addComponent(vaciarLsP_Bttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(37, 60, 89));
        jLabel8.setText("Fecha :");

        out_dateP_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_dateP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_dateP_lb.setText("yyyy-MM-dd");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(37, 60, 89));
        jLabel3.setText("Usuario :");

        out_userP_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_userP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_userP_lb.setText("Usuario 001");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(37, 60, 89));
        jLabel10.setText("Folio de Pedido :");

        out_folioPedP_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_folioPedP_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_folioPedP_lb.setText("Folio-0001");

        proveedorP_jcbx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        proveedorP_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---  seleccione un Proveedor ---" }));
        proveedorP_jcbx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proveedorP_jcbxItemStateChanged(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(37, 60, 89));
        jLabel19.setText("Proveedor");

        addProvedor_lb1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/addUser.png"))); // NOI18N
        addProvedor_lb1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addProvedor_lb1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout RealizrPedido_jpLayout = new javax.swing.GroupLayout(RealizrPedido_jp);
        RealizrPedido_jp.setLayout(RealizrPedido_jpLayout);
        RealizrPedido_jpLayout.setHorizontalGroup(
            RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(proveedorP_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addProvedor_lb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel10)
                    .addComponent(jLabel3))
                .addGap(33, 33, 33)
                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(out_userP_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(out_folioPedP_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(out_dateP_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        RealizrPedido_jpLayout.setVerticalGroup(
            RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(proveedorP_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel19))
                            .addComponent(addProvedor_lb1)))
                    .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RealizrPedido_jpLayout.createSequentialGroup()
                                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(out_folioPedP_lb)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(RealizrPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(out_userP_lb)
                                    .addComponent(out_dateP_lb)))
                            .addGroup(RealizrPedido_jpLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(9, 9, 9)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Realizar Pedidos", RealizrPedido_jp);

        historialPedido_jp.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(37, 60, 89));
        jLabel2.setText("Usuario :");

        out_userH_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_userH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_userH_lb.setText("Usuario 001");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(37, 60, 89));
        jLabel7.setText("Fecha :");

        out_dateH_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_dateH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_dateH_lb.setText("02/05/2023");

        visualizarHistorial_jp.setBackground(new java.awt.Color(255, 255, 255));
        visualizarHistorial_jp.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), "Visualizar Historial por: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        lsHistorialH_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lsHistorialH_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Responsable del Pedido", "Forma de Pago", "Proveedor", "Folio", "Subtotal", "IVA", "Total", "Fecha de Pedido", "Fecha de Entrega"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsHistorialH_tb.setAlignmentX(1.0F);
        lsHistorialH_tb.setAlignmentY(1.0F);
        lsHistorialH_tb.setName(""); // NOI18N
        lsHistorialH_tb.setRowHeight(30);
        jScrollPane2.setViewportView(lsHistorialH_tb);
        if (lsHistorialH_tb.getColumnModel().getColumnCount() > 0) {
            lsHistorialH_tb.getColumnModel().getColumn(1).setMinWidth(130);
            lsHistorialH_tb.getColumnModel().getColumn(1).setMaxWidth(200);
            lsHistorialH_tb.getColumnModel().getColumn(2).setMinWidth(300);
            lsHistorialH_tb.getColumnModel().getColumn(4).setMinWidth(120);
            lsHistorialH_tb.getColumnModel().getColumn(4).setMaxWidth(200);
            lsHistorialH_tb.getColumnModel().getColumn(5).setMinWidth(120);
            lsHistorialH_tb.getColumnModel().getColumn(5).setMaxWidth(200);
            lsHistorialH_tb.getColumnModel().getColumn(6).setMinWidth(120);
            lsHistorialH_tb.getColumnModel().getColumn(6).setMaxWidth(200);
        }

        input_BuscarFolioH_txf.setBackground(new java.awt.Color(37, 60, 89));
        input_BuscarFolioH_txf.setFont(new java.awt.Font("Arial", 1, 25)); // NOI18N
        input_BuscarFolioH_txf.setForeground(new java.awt.Color(255, 255, 255));
        input_BuscarFolioH_txf.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        input_BuscarFolioH_txf.setToolTipText("Folio-");
        input_BuscarFolioH_txf.setCaretColor(new java.awt.Color(255, 255, 255));
        input_BuscarFolioH_txf.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        input_BuscarFolioH_txf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                input_BuscarFolioH_txfKeyReleased(evt);
            }
        });

        search_lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/research.png"))); // NOI18N
        search_lb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_lbMouseClicked(evt);
            }
        });

        VerDetalleH_btnn.setBackground(new java.awt.Color(255, 255, 255));
        VerDetalleH_btnn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        VerDetalleH_btnn.setForeground(new java.awt.Color(37, 60, 89));
        VerDetalleH_btnn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/newProduct.png"))); // NOI18N
        VerDetalleH_btnn.setText("Ver a detalle");
        VerDetalleH_btnn.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        VerDetalleH_btnn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerDetalleH_btnnActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(37, 60, 89));
        jLabel6.setText("Proveedor");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(37, 60, 89));
        jLabel5.setText("Fecha ");

        input_FromDateH_JDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));

        input_ToDateH_JDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));

        proveedorH_jcbx.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        proveedorH_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---  selecciona un Proveedor ---" }));
        proveedorH_jcbx.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                proveedorH_jcbxItemStateChanged(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(37, 60, 89));
        jLabel22.setText("De");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(37, 60, 89));
        jLabel23.setText("a");

        javax.swing.GroupLayout visualizarHistorial_jpLayout = new javax.swing.GroupLayout(visualizarHistorial_jp);
        visualizarHistorial_jp.setLayout(visualizarHistorial_jpLayout);
        visualizarHistorial_jpLayout.setHorizontalGroup(
            visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addComponent(proveedorH_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(12, 12, 12)
                        .addComponent(input_FromDateH_JDate, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(input_ToDateH_JDate, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search_lb))
                    .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(204, 204, 204)))
                .addGap(18, 18, 18)
                .addComponent(input_BuscarFolioH_txf)
                .addGap(27, 27, 27)
                .addComponent(VerDetalleH_btnn))
            .addComponent(jScrollPane2)
        );
        visualizarHistorial_jpLayout.setVerticalGroup(
            visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, visualizarHistorial_jpLayout.createSequentialGroup()
                .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(VerDetalleH_btnn, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                        .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(visualizarHistorial_jpLayout.createSequentialGroup()
                                    .addGap(23, 23, 23)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel22)
                                        .addComponent(input_FromDateH_JDate, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                        .addComponent(jLabel23)
                                        .addComponent(input_ToDateH_JDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, visualizarHistorial_jpLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(visualizarHistorial_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(search_lb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(input_BuscarFolioH_txf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, visualizarHistorial_jpLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(proveedorH_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 31, Short.MAX_VALUE)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89), 2), "Detalles del pedido ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 16), new java.awt.Color(37, 60, 89))); // NOI18N

        lsPedidoH_tb.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        lsPedidoH_tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Barras", "Descripcion del Producto", "Marca del Producto", "Cantidad Pedida", "Precio", "subtotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        lsPedidoH_tb.setRowHeight(30);
        lsPedidoH_tb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lsPedidoH_tbKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(lsPedidoH_tb);
        if (lsPedidoH_tb.getColumnModel().getColumnCount() > 0) {
            lsPedidoH_tb.getColumnModel().getColumn(0).setMinWidth(100);
            lsPedidoH_tb.getColumnModel().getColumn(1).setMinWidth(400);
            lsPedidoH_tb.getColumnModel().getColumn(2).setMinWidth(120);
            lsPedidoH_tb.getColumnModel().getColumn(3).setMinWidth(120);
            lsPedidoH_tb.getColumnModel().getColumn(3).setMaxWidth(150);
            lsPedidoH_tb.getColumnModel().getColumn(4).setMinWidth(150);
            lsPedidoH_tb.getColumnModel().getColumn(4).setMaxWidth(200);
            lsPedidoH_tb.getColumnModel().getColumn(5).setMinWidth(150);
            lsPedidoH_tb.getColumnModel().getColumn(5).setMaxWidth(200);
        }

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

        cancelarActualizacion_bttn.setBackground(new java.awt.Color(255, 255, 255));
        cancelarActualizacion_bttn.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        cancelarActualizacion_bttn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cancelPedido.png"))); // NOI18N
        cancelarActualizacion_bttn.setText("Cancelar Actualización");
        cancelarActualizacion_bttn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActualizacion_bttnActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(37, 60, 89));
        jLabel11.setText("Sub Total ");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(37, 60, 89));
        jLabel12.setText("Iva ");

        jLabel13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(37, 60, 89));
        jLabel13.setText("Total a pagar ");

        out_subTotalH_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_subTotalH_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        out_subTotalH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_subTotalH_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_subTotalH_lb.setText("$ 0.0");
        out_subTotalH_lb.setOpaque(true);

        out_ivaH_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_ivaH_lb.setFont(new java.awt.Font("Arial", 1, 28)); // NOI18N
        out_ivaH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_ivaH_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_ivaH_lb.setText("$ 0.0");
        out_ivaH_lb.setOpaque(true);

        out_totalpagoH_lb.setBackground(new java.awt.Color(255, 255, 255));
        out_totalpagoH_lb.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        out_totalpagoH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_totalpagoH_lb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        out_totalpagoH_lb.setText("$ 0.0");
        out_totalpagoH_lb.setOpaque(true);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(37, 60, 89));
        jLabel4.setText("Nueva Fecha de Entrega");

        frPagoH_Jcbx.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        frPagoH_Jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Forma Pago --" }));

        jLabel9.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(37, 60, 89));
        jLabel9.setText("Forma de Pago");

        jLabel16.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(37, 60, 89));
        jLabel16.setText("Anticipo");

        out_anticipoH_txf.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        out_anticipoH_txf.setForeground(new java.awt.Color(37, 60, 89));
        out_anticipoH_txf.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));

        out_FechEntregaH_Jdate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));
        out_FechEntregaH_Jdate.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(37, 60, 89));
        jLabel24.setText("Estado de Entrega");

        edoEntrgaH_jcbx.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        edoEntrgaH_jcbx.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Estado de Entrega --" }));

        out_folioPedidoH_lb.setFont(new java.awt.Font("Arial", 1, 22)); // NOI18N
        out_folioPedidoH_lb.setForeground(new java.awt.Color(37, 60, 89));
        out_folioPedidoH_lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 60, 89)));

        jLabel25.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(37, 60, 89));
        jLabel25.setText("Folio del Pedido");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)
                        .addComponent(out_FechEntregaH_Jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(frPagoH_Jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(out_anticipoH_txf, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(edoEntrgaH_jcbx, 0, 193, Short.MAX_VALUE)
                            .addComponent(out_folioPedidoH_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(out_subTotalH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(out_ivaH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(out_totalpagoH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(actualizarPedido_bttn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelarActualizacion_bttn, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)))))
                .addGap(0, 0, 0))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(out_FechEntregaH_Jdate, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(frPagoH_Jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel16)
                                        .addComponent(out_anticipoH_txf, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(jLabel4))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(jLabel24)
                                .addGap(28, 28, 28))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(out_folioPedidoH_lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(7, 7, 7)
                                .addComponent(edoEntrgaH_jcbx, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(out_subTotalH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(out_ivaH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(out_totalpagoH_lb, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(actualizarPedido_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelarActualizacion_bttn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout historialPedido_jpLayout = new javax.swing.GroupLayout(historialPedido_jp);
        historialPedido_jp.setLayout(historialPedido_jpLayout);
        historialPedido_jpLayout.setHorizontalGroup(
            historialPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historialPedido_jpLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(43, 43, 43)
                .addComponent(out_userH_lb)
                .addGap(88, 88, 88)
                .addComponent(jLabel7)
                .addGap(37, 37, 37)
                .addComponent(out_dateH_lb))
            .addComponent(visualizarHistorial_jp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        historialPedido_jpLayout.setVerticalGroup(
            historialPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historialPedido_jpLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(historialPedido_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(out_dateH_lb)
                    .addComponent(out_userH_lb)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(visualizarHistorial_jp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Historial Pedidos", historialPedido_jp);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actualizarPedido_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarPedido_bttnActionPerformed

        if(out_FechEntregaH_Jdate !=null && frPagoH_Jcbx.getSelectedIndex() >0 
        && edoEntrgaH_jcbx.getSelectedIndex()>0 && lsPedidoH_tb.getRowCount() > 0){

            String sqldtallepedido =
             "UPDATE detallesPedido set cantProducto = ?, precioC = ?, subTotalPedido = ?"
           + " WHERE idPedido = ? AND idProducto = ?";

            int filas =lsPedidoH_tb.getRowCount() , columnas = 5; // 5 de cantProducto, precioC, subTotalPedido,subTotalPedido,idPedido
            Object[][]  detallesPedido = new Object[filas][columnas];

           // System.out.println("\thacer pedidos");
            for (int i = 0; i < detallesPedido.length; i++) {


                detallesPedido[i][0] =lsPedidoH_tb.getValueAt(i, cantidadP); //cantProducto
                detallesPedido[i][1] =lsPedidoH_tb.getValueAt(i, precioP);//precioC 
                detallesPedido[i][2] =lsPedidoH_tb.getValueAt(i, subtotal);//subTotalPedido
                detallesPedido[i][3] =querying.getIdPedido(out_folioPedidoH_lb.getText());//idPedido
                detallesPedido[i][4] =lsPedidoH_tb.getValueAt(i,0);//idProducto

            }



            String sqlPedido =     
              " UPDATE pedidos set cvFormPago = ?, subTotal = ?, iva = ?, totalPedido = ?, anticipo = ?,"
            + " fechaEntrega = ?, cvTipoEstado = ? "
            + " WHERE folioP = ? AND idPedido = ? ";      


            double[] CalIvaYTotalPago = tbQuerying.tbActn_CalIvaYTotalPago(lsPedidoH_tb,5);
            Date date = out_FechEntregaH_Jdate.getDate();
      

            Object[] pedido = new Object[9];

            pedido[0] = cvFormpagoValidate(frPagoH_Jcbx.getSelectedItem());//cvFormPago
            pedido[1] = CalIvaYTotalPago[0];//subTotal
            pedido[2] = CalIvaYTotalPago[1];//IVA 
            pedido[3] = CalIvaYTotalPago[2]; //totalPedido 
            pedido[4] = Double.valueOf(out_anticipoH_txf.getText());//anticipo 
            pedido[5] = queryingTime.datef(date); //fechaEntrega
            pedido[6] = cvEdoEntregaVal(edoEntrgaH_jcbx.getSelectedItem());//cvTipoEstado
            pedido[7] = out_folioPedidoH_lb.getText();//folioP
            pedido[8] = querying.getIdPedido(out_folioPedidoH_lb.getText());//idPedido
 


            //TRANSACCIONES

            //ENVIO DE PEDIDOS EN DB

            querying.insertIntoDB(detallesPedido,sqldtallepedido);//productos
            querying.insertIntoDB(pedido,sqlPedido);// personas

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

            JOptionPane.showMessageDialog(Pv_Pedidos.this, " ¡ Datos Actualizados con exito ! ");

            //limpiamos tablas
            this.cancelarActualizacion_bttnActionPerformed(evt);
          // cancelActualizacion();
           //disableCompontent(visualizarHistorial_jp,true);



        }else{
            JOptionPane.showMessageDialog(Pv_Pedidos.this, " ¡ Verifique la información ! ");

        } 
        
        
        
        
        
        
        
        
        
        
    }//GEN-LAST:event_actualizarPedido_bttnActionPerformed

    private void lsPedidoH_tbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lsPedidoH_tbKeyReleased

    }//GEN-LAST:event_lsPedidoH_tbKeyReleased

    private void input_BuscarFolioH_txfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_BuscarFolioH_txfKeyReleased

        
        input_FromDateH_JDate.setDate(null);
        input_ToDateH_JDate.setDate(null);     
        proveedorH_jcbx.setSelectedIndex(0);
        
        
        
        
    }//GEN-LAST:event_input_BuscarFolioH_txfKeyReleased

    private void VerDetalleH_btnnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerDetalleH_btnnActionPerformed

        int rowidexSelected = lsHistorialH_tb.getSelectedRow();
        if(rowidexSelected >=0 ){
            
            //DESHABILITAR LA PARTE DE HSITORIAL
            
            
            disableCompontent(visualizarHistorial_jp,false);
            
            // CARGAR LOS DATOS AL DAR DETALLE
         
            
            // cargar los Jcomboox de detalles de pedido para visualizar o modificar al seleccionar un pedido
              String sqljcbx = "SELECT  dsFormPago from cformpago";     
              querying.llenarJcombox(frPagoH_Jcbx, sqljcbx);//fromas de pago
              // *falta llenar estado
              sqljcbx = "SELECT dsTipoEstado from cTipoEstado";
              querying.llenarJcombox(edoEntrgaH_jcbx, sqljcbx);//edo
              
 
            
            String folio = lsHistorialH_tb.getValueAt(rowidexSelected, folioindex).toString();
            out_folioPedidoH_lb.setText(folio);// mostramos el folio
            String sql = "SELECT idProducto,concat(descripcionP,' ',marcaCoche,' ', modeloCoche,' ', anioCoche)"
                       + " AS informacionDelProducto, marcaP,cantProducto, detallespedido.precioC, subTotalPedido"
                       + " FROM productos, detallespedido, pedidos"
                       + " WHERE folioP = '"+folio+"' AND pedidos.idPedido = detallespedido.idPedido "
                       + " AND productos.idProductos = detallespedido.idProducto";
            
            // llenado de tabla
              tbQuerying.tbActn_delAllRows(lsPedidoH_tb);
              int columnCount = lsPedidoH_tb.getColumnCount();
              
              querying.llenarTabla(lsPedidoH_tb, columnCount,sql);
              
              //llenado de los datos de arriba              
              
              sql = " SELECT fechaEntrega, dsFormPago, anticipo, dsTipoEstado"
                  + " FROM pedidos, cformpago, ctipoestado where pedidos.folioP = '"+folio+"' "
                  + " AND cformpago.cvFormPago = pedidos.cvFormPago AND ctipoestado.cvTipoEstado = pedidos.cvTipoEstado";
              
              querying.detallesPedido(sql, out_FechEntregaH_Jdate, frPagoH_Jcbx, out_anticipoH_txf,edoEntrgaH_jcbx);
              
              //llenado del total
              
             int colindexTotal = lsPedidoH_tb.getColumnCount()- 1; // columna de total
             tbQuerying.tbActn_CalsubYTotalPago(lsPedidoH_tb,out_subTotalH_lb,out_ivaH_lb,out_totalpagoH_lb,colindexTotal);
            
        
        }
        

        
        
    }//GEN-LAST:event_VerDetalleH_btnnActionPerformed

    private void proveedorH_jcbxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proveedorH_jcbxItemStateChanged


     /*   if (listPedidoShowP_tb.getRowCount() <= 0 && proveedorP_jcbx.getSelectedIndex() > 0){

            provedorSelected = provedor; // agregamos el provedor mientras no haya producto

        }*/

        
        
        if(proveedorH_jcbx.getSelectedIndex() > 0){
            
        //
        tbQuerying.tbActn_delAllRows(lsHistorialH_tb);// eliminamos filas de las tablas
        input_FromDateH_JDate.setDate(null);
        input_ToDateH_JDate.setDate(null);
        input_BuscarFolioH_txf.setText("");

        String provedor = proveedorH_jcbx.getSelectedItem().toString();
       //  System.out.println("jcbx ProvedorH : "+provedor);

    String sql = 
            
   "SELECT idEmpleado, cFormPago.dsFormPago, "
  +"concat(persona.nombre, ' ', persona.apePaterno, ' ',persona.apeMaterno, ' ', empresa, ' ', puesto)"
  +" as infProveedor,folioP, pedidos.subTotal, IVA, totalPedido, fechaPedido, fechaEntrega" 
  +" FROM pedidos, persona, detalleproveedor, cFormPago"
  +" WHERE pedidos.idProveedor ="+querying.getIdProvedor(provedor)
  +" AND pedidos.idProveedor = persona.idPersona AND detalleproveedor.idProveedor = persona.idPersona "
  + "AND pedidos.cvFormPago = cFormPago.cvFormPago";
            
            
            

            //System.out.println(sql);
        int columnCount = lsHistorialH_tb.getColumnCount();
        querying.llenarTabla(lsHistorialH_tb, columnCount,sql);
        
        }else{
         tbQuerying.tbActn_delAllRows(lsHistorialH_tb);// eliminamos filas de las tablas
        }

    }//GEN-LAST:event_proveedorH_jcbxItemStateChanged

    private void frPagosP_jcbxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_frPagosP_jcbxPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_frPagosP_jcbxPropertyChange

    private void hacerPedidoP_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hacerPedidoP_bttnActionPerformed
 
        if(frPagosP_jcbx.getSelectedIndex()>0 && proveedorP_jcbx.getSelectedIndex()>0 && listPedidoShowP_tb.getRowCount() > 0){

            String sqldtallepedido =
            "insert into detallesPedido (idPedido, idProducto, cantProducto, precioC, subTotalPedido)\n" +
            "VALUES(?,?,?,?,?)";

            int filas =listPedidoShowP_tb.getRowCount() , columnas = 5; // 5 de idProducto, cantProducto, precioC,subTotalPedido
            Object[][]  detallesPedido = new Object[filas][columnas];

            System.out.println("\thacer pedidos");
            for (int i = 0; i < detallesPedido.length; i++) {

                /**
                0,2,3,8 posiciones de  idProducto, cantProducto, precioC,subTotalPedido en la tabla
                tambien podemos obtener los indices con getname("nombre de la columna a buscar pero no es necesario por el momento")
                */
                detallesPedido[i][0] =idPedidoyfolio; //idPedido
                detallesPedido[i][1] =listPedidoShowP_tb.getValueAt(i,0);//idProducto
                detallesPedido[i][2] =listPedidoShowP_tb.getValueAt(i, cantidadP); //cantProducto
                detallesPedido[i][3] =listPedidoShowP_tb.getValueAt(i, precioP);//precioC
                detallesPedido[i][4] =listPedidoShowP_tb.getValueAt(i, 5);//subTotalPedido

            }

            /*   for (Object[] fila : detallesPedido) {
                for (Object elemento : fila) {
                    // Realiza alguna operación con el elemento
                    System.out.print(elemento +" ");
                }
                System.out.println("");
            }*/

            String sqlPedido =
            "insert into pedidos (idEmpleado, cvFormPago, idProveedor, folioP, subTotal, IVA, totalPedido, anticipo, fechaPedido, fechaEntrega, cvTipoEstado)" +
            "values(?,?,?,?,?,?,?,?,?,?,?)";


            double[] CalIvaYTotalPago = tbQuerying.tbActn_CalIvaYTotalPago(listPedidoShowP_tb,5);
            Date date = input_dateEntregaP_Jdate.getDate();
      

            Object[] pedido = new Object[11];

            pedido[0] = idEmpleado;//idEmpleado
            pedido[1] = cvFormpagoValidate(frPagosP_jcbx.getSelectedItem());//cvFormPago
            pedido[2] = querying.getIdProvedor(proveedorP_jcbx.getSelectedItem().toString());//idProveedor
            pedido[3] = Generarfolio(idPedidoyfolio);//folioP
            pedido[4] = CalIvaYTotalPago[0]; //subTotal
            pedido[5] = CalIvaYTotalPago[1];//IVA
            pedido[6] = CalIvaYTotalPago[2]; //totalPedido
            pedido[7] = Integer.valueOf(input_AnticipoP_jtxf.getText());//anticipo
            pedido[8] = queryingTime.date("yyyy-MM-dd");//fechaPedido
            pedido[9] = queryingTime.datef(date); //fechaEntrega
            pedido[10]= "Cam";//cvTipoEstado

            /*
            System.out.println("*******2consulta");

            for( Object fila : pedido){
                System.out.print(fila+" ");
            }*/

            //TRANSACCIONES

            //ENVIO DE PEDIDOS EN DB

            querying.insertIntoDB(detallesPedido,sqldtallepedido);//productos
            querying.insertIntoDB(pedido,sqlPedido);// personas

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

            JOptionPane.showMessageDialog(Pv_Pedidos.this, " ¡ Datos guardado con exito ! ");

            //limpiamos tablas
            cleanALL();

            //generamos el nuevo folio
            getidProvedorYfolio();
            out_folioPedP_lb.setText(Generarfolio(idPedidoyfolio));

        }else{
            JOptionPane.showMessageDialog(Pv_Pedidos.this, " ¡ Seleccione los datos correctamente! ");

        }

    }//GEN-LAST:event_hacerPedidoP_bttnActionPerformed

    private void listPedidoShowP_tbKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_listPedidoShowP_tbKeyReleased
    char tecla = evt.getKeyChar();

        if(tecla == KeyEvent.VK_ENTER){

            int rowselected = listPedidoShowP_tb.getSelectedRow();

            // ene sta ocacion 2 y 3 es el cantidad y precio index

            tbQuerying.tbActn_CalTotalProducto(listPedidoShowP_tb, rowselected,cantidadP,precioP);
            subYtotalAPagar();

        }
    }//GEN-LAST:event_listPedidoShowP_tbKeyReleased

    private void deleteP_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteP_bttnActionPerformed
       
        quitarLista();
    }//GEN-LAST:event_deleteP_bttnActionPerformed

    private void vaciarLsP_BttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vaciarLsP_BttnActionPerformed
       
        tbQuerying.tbActn_delAllRows(listPedidoShowP_tb);
        subYtotalAPagar();
        provedorSelected = "";
    }//GEN-LAST:event_vaciarLsP_BttnActionPerformed

    private void input_BarraBuscarP_txfKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_input_BarraBuscarP_txfKeyReleased
        
        String provedor = proveedorP_jcbx.getSelectedItem().toString();
        //System.out.println("-----provedor "+provedor);
        searchProduct(provedor);             
        
    }//GEN-LAST:event_input_BarraBuscarP_txfKeyReleased

    private void addProductP_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProductP_bttnActionPerformed
         
    // AGREGAR DATOS A LA TABLA PEDIDOS
        int rowIndex = listPrdctAddP_tb.getSelectedRow(); // Número de fila que deseas obtener

        if(rowIndex >= 0 && itemSameProvedor()){

            if(! itemsRepetidos(rowIndex) )
            tbQuerying.tbActn_Pedido_addRow(listPrdctAddP_tb, listPedidoShowP_tb, rowIndex);

        }else       
            if(rowIndex < 0 ){
                JOptionPane.showMessageDialog(Pv_Pedidos.this, " Seleccione un producto ");
        }else{
            JOptionPane.showMessageDialog(Pv_Pedidos.this, " Solo puede hacer pedidos de un Proveedor ");
            proveedorP_jcbx.setSelectedItem(provedorSelected);
            }
        // CALCULAR PAGO TOTAL
        subYtotalAPagar();       
        
    }//GEN-LAST:event_addProductP_bttnActionPerformed

    private void newPedidosP_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newPedidosP_bttnActionPerformed


        Pv_Inventario inventarioW = new Pv_Inventario();
        Pv_MenuGeneral.bgMenuGeneral_jp.add(inventarioW);
        inventarioW.toFront();//visualizar enfrente
        inventarioW.setVisible(true);
    }//GEN-LAST:event_newPedidosP_bttnActionPerformed

    private void proveedorP_jcbxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_proveedorP_jcbxItemStateChanged
     
        tbQuerying.tbActn_delAllRows(listPrdctAddP_tb);// eliminamos filas de las tablas

        String provedor = proveedorP_jcbx.getSelectedItem().toString();

        if (listPedidoShowP_tb.getRowCount() <= 0 && proveedorP_jcbx.getSelectedIndex() > 0){

            provedorSelected = provedor; // agregamos el provedor mientras no haya producto

        }

         System.out.println("jcbx Provedor : "+provedor);
        
        if(proveedorP_jcbx.getSelectedIndex() > 0){
            


    String sql = "SELECT idProductos, concat(descripcionP,'  ', marcaCoche, ' ', modeloCoche, ' ', anioCoche)"
            + "as infProducto, marcaP, cantidadP, precioC from productos, persona" 
            + " where idPersona = "+querying.getIdProvedor(provedor)+" AND idProveedor = idPersona";
            //System.out.println(sql);
            
        int columnCount = listPrdctAddP_tb.getColumnCount();
        querying.llenarTabla(listPrdctAddP_tb, columnCount,sql);     
    
        
        }

        
    }//GEN-LAST:event_proveedorP_jcbxItemStateChanged

    private void addProvedor_lb1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addProvedor_lb1MouseClicked
      
        Pv_DatosPersona personasW = new Pv_DatosPersona();
        Pv_MenuGeneral.bgMenuGeneral_jp.add(personasW);
        personasW.toFront();//visualizar enfrente
        personasW.setVisible(true);
        
        
        
    }//GEN-LAST:event_addProvedor_lb1MouseClicked

    private void cancelarPedidoP_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarPedidoP_bttnActionPerformed
     cleanALL();
    }//GEN-LAST:event_cancelarPedidoP_bttnActionPerformed

    private void search_lbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_lbMouseClicked

        System.out.println( lsPedidoH_tb.getRowCount());
        if(lsPedidoH_tb.getRowCount()<1){
           
            
            proveedorH_jcbx.setSelectedIndex(0);
            input_BuscarFolioH_txf.setText("");
            searchPedByFech();
        
        }
        

    }//GEN-LAST:event_search_lbMouseClicked

    private void cancelarActualizacion_bttnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActualizacion_bttnActionPerformed
        System.out.println(lsPedidoH_tb.getRowCount());
     if(lsPedidoH_tb.getRowCount() > 0){
         
          disableCompontent(visualizarHistorial_jp,true);
          cancelActualizacion();
     
     }   

    }//GEN-LAST:event_cancelarActualizacion_bttnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel RealizrPedido_jp;
    private javax.swing.JButton VerDetalleH_btnn;
    private javax.swing.JButton actualizarPedido_bttn;
    private javax.swing.JButton addProductP_bttn;
    private javax.swing.JLabel addProvedor_lb1;
    private javax.swing.JButton cancelarActualizacion_bttn;
    private javax.swing.JButton cancelarPedidoP_bttn;
    private javax.swing.JButton deleteP_bttn;
    private javax.swing.JComboBox<String> edoEntrgaH_jcbx;
    private javax.swing.JComboBox<String> frPagoH_Jcbx;
    public javax.swing.JComboBox<String> frPagosP_jcbx;
    private javax.swing.JButton hacerPedidoP_bttn;
    private javax.swing.JPanel historialPedido_jp;
    private javax.swing.JTextField input_AnticipoP_jtxf;
    private javax.swing.JTextField input_BarraBuscarP_txf;
    private javax.swing.JTextField input_BuscarFolioH_txf;
    private com.toedter.calendar.JDateChooser input_FromDateH_JDate;
    private com.toedter.calendar.JDateChooser input_ToDateH_JDate;
    private com.toedter.calendar.JDateChooser input_dateEntregaP_Jdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTable listPedidoShowP_tb;
    public javax.swing.JTable listPrdctAddP_tb;
    public javax.swing.JTable lsHistorialH_tb;
    public javax.swing.JTable lsPedidoH_tb;
    private javax.swing.JButton newPedidosP_bttn;
    private com.toedter.calendar.JDateChooser out_FechEntregaH_Jdate;
    private javax.swing.JTextField out_anticipoH_txf;
    private javax.swing.JLabel out_dateH_lb;
    private javax.swing.JLabel out_dateP_lb;
    private javax.swing.JLabel out_folioPedP_lb;
    private javax.swing.JLabel out_folioPedidoH_lb;
    private javax.swing.JLabel out_ivaH_lb;
    private javax.swing.JLabel out_ivaP_lb;
    private javax.swing.JLabel out_subTotalH_lb;
    private javax.swing.JLabel out_subTotalP_lb;
    private javax.swing.JLabel out_totalpagoH_lb;
    private javax.swing.JLabel out_totalpagoP_lb;
    private javax.swing.JLabel out_userH_lb;
    private javax.swing.JLabel out_userP_lb;
    public javax.swing.JComboBox<String> proveedorH_jcbx;
    public javax.swing.JComboBox<String> proveedorP_jcbx;
    private javax.swing.JLabel search_lb;
    private javax.swing.JLabel search_lb3;
    private javax.swing.JButton vaciarLsP_Bttn;
    private javax.swing.JPanel visualizarHistorial_jp;
    // End of variables declaration//GEN-END:variables
}
