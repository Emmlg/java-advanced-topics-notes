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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author lopez
 */
public class Banco {
    
    private final double[] cuentas;
    
    private Lock cierraBanco= new ReentrantLock();
 

    public Banco() {
    cuentas = new double [100];
        for (int i = 0; i < cuentas.length; i++) {
            cuentas[i]=200;
        }
    }
    
    
    
  // metodos
    
    public void transeferencia(int ctaOrigen,int ctaDestino,double cantidad){
        cierraBanco.lock();
        
        try {
            

        if(cuentas[ctaOrigen]<cantidad){// evalua que el saldo no es inferior a la transefrencia
        
            System.out.println("----------CUENTA INSUFICIENTE : "+ctaOrigen+"--- SALDO : "+cuentas[ctaOrigen]+"----- "+cantidad);
            return;
        }else
                System.out.println("-----cantidad ok ---------"+ctaOrigen);
        
        
        System.out.println(Thread.currentThread());
        
        cuentas[ctaOrigen ]-= cantidad; //dinero que sale de la ceunta origen
        
        System.out.printf("%10.2f de %d para %d ",cantidad,ctaOrigen,ctaDestino);
    
        cuentas[ctaDestino] +=cantidad;
          
        System.out.printf("saldo total : %10.2f%n ",getSaldoTotal() );
    
        }finally{
        
        cierraBanco.unlock();
        
        }
        
        
        }
    
    public double getSaldoTotal(){
    
        double suma_cuentas=0;
        
        for(double a:cuentas){
        
        suma_cuentas += a;
        }
        return suma_cuentas;
    
    }
    
    
    
    
    
    
    
}
