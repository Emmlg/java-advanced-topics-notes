/**
 * Tema: 02 Simulacion Banco Sincronizado
 * Conceptos: Simulacion Banco Sincronizado
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;



/**
 *
 * @author lopez
 */
public class EjecucionTransferencia implements Runnable{
    
    private Banco banco;
    private int deLaCuenta;
    private double cantidadMax;

    public EjecucionTransferencia(Banco b,int de,double max) {
      
        banco=b;
        de=deLaCuenta;
        cantidadMax=max;
    
    }
    
    
    

    @Override
    public void run() {
                   
            

            try {
                     
             while (true) {
                 
               int paraLaCuenta = (int)(100*Math.random());
               double cantidad= cantidadMax*Math.random();
               banco.transeferencia(deLaCuenta,paraLaCuenta,cantidad);       
              
               Thread.sleep(2000);
             }
                
            } catch (InterruptedException ex) {
                
                System.out.println(ex.getMessage() );
            }
            
        
    }
    
    
    
}
