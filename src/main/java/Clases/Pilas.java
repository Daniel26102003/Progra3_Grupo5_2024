package Clases;

import java.util.Scanner;
import java.util.Stack;

public class Pilas {

    Stack<String> pila = new Stack();

    public void Agregar_Pila() {
        Scanner elemento = new Scanner(System.in);
        String dato = elemento.nextLine();
        pila.push(dato);
    }
       
    public void Primer_elemento_pila() {
        System.out.println(pila.peek());
    }

    public void Eliminar_primer_elemento_pila() {
        System.out.println(pila.pop());
    }

    public void Buscar_elemento_Pila() {
        System.out.println("INGRESA EL DATO A BUSCAR");
        Scanner elementob = new Scanner(System.in);
        String datob = elementob.nextLine();
        System.out.println(pila.search(datob));
    }

    public void Tamaño_pila(){
    System.out.println(pila.size());
    }
    
    public void Mostrar_pila(){
    System.out.println(pila);
    }
    
}