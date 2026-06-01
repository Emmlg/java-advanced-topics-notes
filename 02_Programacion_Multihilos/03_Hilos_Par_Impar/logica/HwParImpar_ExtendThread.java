/**
 * Tema: 03 Hilos Par Impar
 * Conceptos: Hilos Par Impar
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
public class HwParImpar_ExtendThread extends Thread{
    
    @Override
    public void run(){
       long ini=System.currentTimeMillis();   
        
        System.out.println("Numero Par");
        for (int i = 1; i <= 10; i++) {
            if(i%2==0)
                System.out.println("numeros par : "+i);
            try {
                Thread.sleep(1200);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    
      long fin=System.currentTimeMillis();   
          System.out.println("Tiempo de ejecucion de hilo 1 es: "+(fin-ini)/1000);
    }
    
    public static void main(String[] args) {
       
      
      HwParImpar_ExtendThread h1=new HwParImpar_ExtendThread();
      h1.start();
     
      
      long ini1=System.currentTimeMillis();
        System.out.println("numeros impares ");
        
        for (int i = 1; i <= 10; i++) {
            if(i !=0)
            System.out.println("numero impar : "+i);
        try {
                Thread.sleep(900);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        
      long fin1=System.currentTimeMillis();
      
      
        System.out.println("Tiempo de ejecucion de hilo 2 es: "+(fin1-ini1)/1000);
        
    }
    
    
}
