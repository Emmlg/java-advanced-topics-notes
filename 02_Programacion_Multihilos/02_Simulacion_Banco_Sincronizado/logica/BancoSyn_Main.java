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
public class BancoSyn_Main {
    public static void main(String[] args) {
        Banco b=new Banco();
        
        for(int i=0;i<100;i++){
        
            EjecucionTransferencia r= new EjecucionTransferencia(b, i,200);
            Thread t=new Thread(r);
            t.start();
        }
    }
    
}
