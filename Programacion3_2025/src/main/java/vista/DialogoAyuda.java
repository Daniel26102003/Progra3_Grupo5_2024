/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 
 * @author Erik
 */
public class DialogoAyuda extends JDialog {
    
   
    public DialogoAyuda(Frame parent) {
        super(parent, "Ayuda - Hoja de Cálculo", true);
        inicializarComponentes();
        pack();
        setLocationRelativeTo(parent);
    }
    
    //Inicializa los componentes del diálogo
     
    private void inicializarComponentes() {
        // Panel principal con borde
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel de contenido para las instrucciones
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        
        // Título
        JLabel titulo = new JLabel("Cómo usar las fórmulas:");
        titulo.setFont(new Font("Dialog", Font.BOLD, 14));
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContenido.add(titulo);
        panelContenido.add(Box.createVerticalStrut(10));
        
        // Instrucciones
        String[] instrucciones = {
            "1. Inicia con '=' para crear una fórmula",
            "2. Usa operadores: +, -, *, /",
            "3. Usa paréntesis para agrupar operaciones: (A1+B1)*C1",
            "4. Referencia celdas por su nombre (A1, B2, etc.)",
            "5. Las referencias a celdas no distinguen entre mayúsculas y minúsculas (A1 y a1 son iguales)",
            "6. Ejemplos:",
            "   =A1+B1 (suma A1 y B1)",
            "   =A1-B1 (resta B1 de A1)",
            "   =A1*B1 (multiplica A1 por B1)",
            "   =A1/B1 (divide A1 entre B1)",
            "   =(A1+B1)*C1 (suma A1 y B1, luego multiplica por C1)",
            "   =Sumar(A1:A5) (suma el rango A1 a A5)",
            "7. Presiona Enter para calcular",
            "8. Usa las flechas del teclado para navegar entre celdas"
        };
        
        for (String instruccion : instrucciones) {
            JLabel label = new JLabel(instruccion);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(label);
            panelContenido.add(Box.createVerticalStrut(5));
        }
        
        // Scroll pane para el contenido
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        // Botón de cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> setVisible(false));
        
        // Panel para el botón
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnCerrar);
        
        // Agregar componentes al panel principal
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        // Agregar panel al diálogo
        setContentPane(panel);
        
        // Configurar comportamiento al cerrar
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    
    //Agrega contenido adicional al diálogo de ayuda
     
    public void agregarContenido(String titulo, String[] contenido) {
        // Este método permite agregar más contenido al diálogo de ayuda
        // para futuras expansiones
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        
        JLabel labelTitulo = new JLabel(titulo);
        labelTitulo.setFont(new Font("Dialog", Font.BOLD, 14));
        labelTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelContenido.add(labelTitulo);
        panelContenido.add(Box.createVerticalStrut(10));
        
        for (String linea : contenido) {
            JLabel label = new JLabel(linea);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelContenido.add(label);
            panelContenido.add(Box.createVerticalStrut(5));
        }
    }
}

