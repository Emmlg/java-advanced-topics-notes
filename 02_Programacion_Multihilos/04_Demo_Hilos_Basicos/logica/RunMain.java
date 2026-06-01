/**
 * Tema: 04 Demo Hilos Basicos
 * Conceptos: Demo Hilos Basicos
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
public class RunMain {
    public static void main(String[] args) {
        System.out.println("Inicio del HILO");
        MyThread firstThread=new MyThread(" #1");
        firstThread.start();
        
        for (int i = 0; i < 10; i++) {
         
            System.out.println(".");
            try {
                Thread.sleep(1400);
                
            } catch (InterruptedException e) {
                System.out.println("");  
            }
            
            
        }
        
        
        
    }
    
}
