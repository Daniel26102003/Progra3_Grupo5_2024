/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.HojaCalculo;
import modelo.Celda;
import vista.InterfazGraficaHojaCalculo;

/**
 * Clase Controlador que conecta el modelo con la vista
 * 
 */
public class Controlador {
    
    // Atributos del controlador
    private HojaCalculo modelo;
    private InterfazGraficaHojaCalculo vista;
    
    //contructor del controlador
    public Controlador(HojaCalculo modelo, InterfazGraficaHojaCalculo vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        // Establecer el controlador en la vista
        this.vista.setControlador(this);
    }
    
    //Obtiene una celda del modelo numero de fila y columna
    public Celda obtenerCelda(int fila, int columna) {
        return modelo.obtenerCelda(fila, columna);
    }
    
    // Establece un valor en una celda del modelo
     
    public void establecerValor(int fila, int columna, String valor) {
        modelo.establecerValor(fila, columna, valor);
    }
    
    // Elimina una celda del modelo
     
    public boolean eliminarCelda(int fila, int columna) {
        return modelo.eliminarCelda(fila, columna);
    }
    
    // Evalúa una fórmula
     
    public double evaluarFormula(String formula) {
        return modelo.evaluarFormula(formula);
    }
    //Convierte una referencia de celda a coordenadas
    
    public int[] convertirReferenciaCelda(String referencia) {
        return modelo.convertirReferenciaCelda(referencia);
    }
    
    //Convierte coordenadas a una referencia de celda
     
    public String convertirCoordenadaAReferencia(int fila, int columna) {
        return modelo.convertirCoordenadaAReferencia(fila, columna);
    }
}

