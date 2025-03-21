/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Erik
 */
public class Celdas {
    private String valor;
    private int fila;
    private int columna;
    private Celdas derecha;
    private Celdas abajo;
    
    public Celdas (int fila, int columna, String valor){
    this.fila = fila;
    this.columna = columna;
    this.valor = valor;
    this.derecha = null;
    this.abajo = null;
    }
    
   // get y set

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public Celdas getDerecha() {
        return derecha;
    }

    public void setDerecha(Celdas derecha) {
        this.derecha = derecha;
    }

    public Celdas getAbajo() {
        return abajo;
    }

    public void setAbajo(Celdas abajo) {
        this.abajo = abajo;
    }
    
    // creacion de valor cadena
    
    public String toString(){
    return valor;
    }
            
}
