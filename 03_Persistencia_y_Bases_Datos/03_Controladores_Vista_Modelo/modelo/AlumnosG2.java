/**
 * Tema: 03 Controladores Vista Modelo
 * Conceptos: Controladores Vista Modelo
 * Autor: Emmlg
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author lopez
 */
public class AlumnosG2 {
    
    private String  Nombre, ApePat, ApeMat, Genero, FecNac ;
    private  int CvAlum ;

    public AlumnosG2() {
    
        Nombre = ApePat = ApeMat = Genero = FecNac ="";
        CvAlum = 0;
        
    
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApePat() {
        return ApePat;
    }

    public void setApePat(String ApePat) {
        this.ApePat = ApePat;
    }

    public String getApeMat() {
        return ApeMat;
    }

    public void setApeMat(String ApeMat) {
        this.ApeMat = ApeMat;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getFecNac() {
        return FecNac;
    }

    public void setFecNac(String FecNac) {
        this.FecNac = FecNac;
    }

    public int getCvAlum() {
        return CvAlum;
    }

    public void setCvAlum(int CvAlum) {
        this.CvAlum = CvAlum;
    }
    
    
    
    
    
    
    
    
}
