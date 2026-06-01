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
public class MyThread extends Thread{

    public MyThread(String nombre) {
        super(nombre);

    }
    

    
    public void run(){
    
        System.out.println(getName()+" iniciando.");   
        
        try {
            for(int i=0;i<10;i++){
            Thread.sleep(400);
                System.out.println("En "+getName()+", el recuento es "+i);
            }
        } catch (InterruptedException e) {
            
            System.out.println(getName()+" interrumpido.");
        }
        System.out.println(getName()+"finalizado");
    }
    
    
    // fin de class
}
