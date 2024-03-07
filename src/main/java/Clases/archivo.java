/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author skhem
 */
public class archivo {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Por favor, ingrese la ruta del archivo de texto:");
        String rutaArchivo = scanner.nextLine();

        cargarArchivoBD(rutaArchivo);

        scanner.close(); // Cerrar el scanner al finalizar
    }

    public static void cargarArchivoBD(String rutaArchivo) {
        try {
            File archivo = new File(rutaArchivo);
            Scanner lectorArchivo = new Scanner(archivo);

            while (lectorArchivo.hasNextLine()) {
                String linea = lectorArchivo.nextLine();
                if (validarFormato(linea)) {
                    String[] numeros = linea.split(",");
                    for (String numero : numeros) {
                        int num = Integer.parseInt(numero);
                        // Aquí iría la lógica para cargar el número en la base de datos
                        System.out.println("Número cargado en la base de datos: " + num);
                    }
                } else {
                    System.out.println("El archivo no tiene el formato correcto. Verifique que contenga solo números separados por comas.");
                }
            }
            lectorArchivo.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no pudo ser encontrado.");
        }
    }

    private static boolean validarFormato(String linea) {
        // Verificar que la línea contenga solo números separados por comas
        return linea.matches("^\\d+(,\\d+)*$");
    }
}
    

