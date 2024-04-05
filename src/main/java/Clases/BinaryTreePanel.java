/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Esaú
 */
import javax.swing.*;
import java.awt.*;

public class BinaryTreePanel extends JPanel {
    private BinaryTree tree;

    public BinaryTreePanel(BinaryTree tree) {
        this.tree = tree;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, getWidth() / 2, 30, tree.getRoot(), getWidth() / 4);
    }

    private void drawTree(Graphics g, int x, int y, BinaryTree.Node node, int xOffset) {
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
}

