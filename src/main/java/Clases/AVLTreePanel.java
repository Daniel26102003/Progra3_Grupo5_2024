/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author skhem
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AVLTreePanel extends JPanel {
    private AVLTree tree;

    public AVLTreePanel(AVLTree tree) {
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, getWidth() / 2, 30, tree.getRoot(), getWidth() / 4);
    }

    private void drawTree(Graphics g, int x, int y, AVLTree.Node node, int xOffset) {
        if (node == null)
            return;

        int circleSize = 30;
        int textOffset = 5;

        g.setColor(Color.BLACK);
        g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize, circleSize);
        g.setColor(Color.WHITE);
        g.drawString(Integer.toString(node.value), x - g.getFontMetrics().stringWidth(Integer.toString(node.value)) / 2, y + textOffset);

        if (node.left != null) {
            int xLeft = x - xOffset;
            int yLeft = y + 60;
            g.setColor(Color.BLACK);
            g.drawLine(x, y, xLeft, yLeft);
            drawTree(g, xLeft, yLeft, node.left, xOffset / 2);
        }
        if (node.right != null) {
            int xRight = x + xOffset;
            int yRight = y + 60;
            g.setColor(Color.BLACK);
            g.drawLine(x, y, xRight, yRight);
            drawTree(g, xRight, yRight, node.right, xOffset / 2);
        }
    }

    public void addNode(int value) {
        tree.insert(value);
        repaint();
    }

    public void insertarNodo(int valor) {
        addNode(valor);
    }
    
    public void agregarMetodoBoton(JButton boton, JTextField campoTexto) {
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int valor = Integer.parseInt(campoTexto.getText());
                    insertarNodo(valor);
                    campoTexto.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AVLTreePanel.this, "Por favor, ingresa un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}