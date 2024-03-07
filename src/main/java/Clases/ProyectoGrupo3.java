/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Clases;
import java.util.Scanner;
/**
 *
 * @author Esaú
 */
public class ProyectoGrupo3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    arbolesBinarios(scanner);
                    break;
                case 2:
                    System.out.println("Segunda opción: XX");
                    break;
                case 3:
                    System.out.println("Tercera opción: YY");
                    break;
                case 4:
                    System.out.println("Salir del programa...");
                    return;
                default:
                    System.out.println("Opción no es válida. Por favor, seleccione una opción válida.");
            }
        }
        
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("\n Proyecto Programación 3:\n");
        System.out.println("\n1. Árboles binarios");
        System.out.println("2. XX");
        System.out.println("3. YY");
        System.out.println("4. Salir \n"); 
        System.out.print("Seleccione una opción: \n");
    }

    public static void mostrarSubMenuArbolesBinarios() {
        System.out.println("\n1. Cargar archivo a BD");
        System.out.println("2. Generar Árbol");
        System.out.println("3. Imprimir Árbol");
        System.out.println("4. Limpiar base de datos");
        System.out.println("5. Volver al menú principal");
        System.out.print("\nSeleccione una opción: ");
    }
    
public static void arbolesBinarios(Scanner scanner) {
        while (true) {
            mostrarSubMenuArbolesBinarios();
            int opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.println("Opción seleccionada: Cargar archivo a BD");
                    // Lógica para cargar el archivo a la base de datos
                    break;
                case 2:
                    System.out.println("Opción seleccionada: Generar Árbol");
                    // Lógica para generar el árbol
                    break;
                case 3:
                    System.out.println("Opción seleccionada: Imprimir Árbol");
                    // Lógica para imprimir el árbol
                    break;
                case 4:
                    System.out.println("Opción seleccionada: Limpiar base de datos");
                    // Lógica para limpiar la base de datos
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
    }
}
