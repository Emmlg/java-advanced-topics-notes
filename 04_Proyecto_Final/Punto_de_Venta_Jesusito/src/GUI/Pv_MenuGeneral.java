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







import java.util.logging.*;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


/**
 *
 * @author lopez
 */
public class Pv_MenuGeneral extends javax.swing.JFrame {


    
    
    public Pv_MenuGeneral() {
        initComponents();
        
               
    }


    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgMenuGeneral_jp = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        venta_jmenu = new javax.swing.JMenu();
        inventario_jmenu = new javax.swing.JMenu();
        corteCaja_jmenu = new javax.swing.JMenu();
        pedidos_jmenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Refaccionaria Jesusito");
        setAlwaysOnTop(true);
        setExtendedState(6);
        setName("Main"); // NOI18N

        bgMenuGeneral_jp.setBackground(new java.awt.Color(37, 60, 89));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fish.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Refaccionaria Jesusito !");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("¡ Bienvenido(a)");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        bgMenuGeneral_jp.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        bgMenuGeneral_jp.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        bgMenuGeneral_jp.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout bgMenuGeneral_jpLayout = new javax.swing.GroupLayout(bgMenuGeneral_jp);
        bgMenuGeneral_jp.setLayout(bgMenuGeneral_jpLayout);
        bgMenuGeneral_jpLayout.setHorizontalGroup(
            bgMenuGeneral_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgMenuGeneral_jpLayout.createSequentialGroup()
                .addGap(662, 662, 662)
                .addGroup(bgMenuGeneral_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bgMenuGeneral_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bgMenuGeneral_jpLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel3)))
                .addContainerGap(638, Short.MAX_VALUE))
        );
        bgMenuGeneral_jpLayout.setVerticalGroup(
            bgMenuGeneral_jpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bgMenuGeneral_jpLayout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addContainerGap(406, Short.MAX_VALUE))
        );

        getContentPane().add(bgMenuGeneral_jp, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        venta_jmenu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        venta_jmenu.setText("Venta");
        venta_jmenu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        venta_jmenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        venta_jmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                venta_jmenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(venta_jmenu);

        inventario_jmenu.setText("Inventario");
        inventario_jmenu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        inventario_jmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                inventario_jmenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(inventario_jmenu);

        corteCaja_jmenu.setText("Corte de Caja");
        corteCaja_jmenu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jMenuBar1.add(corteCaja_jmenu);

        pedidos_jmenu.setText("Pedidos");
        pedidos_jmenu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        pedidos_jmenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pedidos_jmenuMouseClicked(evt);
            }
        });
        jMenuBar1.add(pedidos_jmenu);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void venta_jmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_venta_jmenuMouseClicked

         
         Pv_Ventas Jinter_ventas = new Pv_Ventas();
          AddJinternal addwjf = new AddJinternal();
         addwjf.addJinternals(Jinter_ventas);
         
      //   addJinternals(Jinter_ventas);
        
    }//GEN-LAST:event_venta_jmenuMouseClicked

    private void pedidos_jmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pedidos_jmenuMouseClicked

       
      Pv_Pedidos Jinter_pedidos = new Pv_Pedidos();
      AddJinternal addwjf = new AddJinternal();
      addwjf.addJinternals(Jinter_pedidos); 
        
     
      
    }//GEN-LAST:event_pedidos_jmenuMouseClicked

    private void inventario_jmenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_inventario_jmenuMouseClicked
     

        Pv_Inventario Jinter_Inventario = new Pv_Inventario();
        AddJinternal addwjf = new AddJinternal();        
        addwjf.addJinternals(Jinter_Inventario);
        
        
    }//GEN-LAST:event_inventario_jmenuMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

   

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                try {
                    UIManager.setLookAndFeel(new MetalLookAndFeel());
                    
                } catch (UnsupportedLookAndFeelException ex) {
                    
                    Logger.getLogger(Pv_MenuGeneral.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                new Pv_MenuGeneral().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane bgMenuGeneral_jp;
    private javax.swing.JMenu corteCaja_jmenu;
    private javax.swing.JMenu inventario_jmenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu pedidos_jmenu;
    private javax.swing.JMenu venta_jmenu;
    // End of variables declaration//GEN-END:variables
}
