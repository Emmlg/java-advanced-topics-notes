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
package vista;

import controlador.*;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author lopez
 */
public class Marco_Aplicacion extends JFrame{
    
    // Variables
    
        private  Connection miConexion;
	
	public JComboBox secciones;
	
	public JComboBox FecNac;
	
	private JTextArea resultado;

    public Marco_Aplicacion() {
    
        setTitle ("Consulta BBDD");
		
		setBounds(500,300,400,400);
		
		setLayout(new BorderLayout());
		
		JPanel menus=new JPanel();
		
		menus.setLayout(new FlowLayout());
		
		secciones=new JComboBox();
		
		secciones.setEditable(false);
		
		secciones.addItem("Todos");
		
		FecNac=new JComboBox();
		
		FecNac.setEditable(false);
		
		FecNac.addItem("Todos");
		
		resultado= new JTextArea(4,50);
		
		resultado.setEditable(false);
		
		add(resultado);
		
		menus.add(secciones);
		
		menus.add(FecNac);	
		
		add(menus, BorderLayout.NORTH);
		
		add(resultado, BorderLayout.CENTER);
		
		JButton botonConsulta=new JButton("Consulta");
                
                add(botonConsulta, BorderLayout.SOUTH);
                
                addWindowListener(new  ControlarSecciones(this));

    
    }
    
    
}
