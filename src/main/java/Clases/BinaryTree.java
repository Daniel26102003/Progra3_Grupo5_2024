/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Esaú
 */
public class BinaryTree {
    static class Node {
        int value;
        Node left, right;
        private String data;

        Node(int value) {
            this.value = value;
            left = right = null;
        }
    }
    
    private Node root;

    public BinaryTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node root, int value) {
        if (root == null) {
            root = new Node(value);
            return root;
        }
        if (value < root.value)
            root.left = insertRec(root.left, value);
        else if (value > root.value)
            root.right = insertRec(root.right, value);
        return root;
    }
    
    public String Postorden() {
        return Postorden(root);
    }

    private String Postorden(Node node) {
        StringBuilder result = new StringBuilder();
        if (node != null) {
            result.append(Postorden(node.left));
            result.append(Postorden(node.right));
            result.append(node.value).append(" ");
        }
        return result.toString();
    }

    public String Inorden() {
        return Inorden(root);
    }

    private String Inorden(Node node) {
        StringBuilder result = new StringBuilder();
        if (node != null) {
            result.append(Inorden(node.left));
            result.append(node.value).append(" ");
            result.append(Inorden(node.right));
        }
        return result.toString();
    }

    public String Preorden() {
        return Preorden(root);
    }

    private String Preorden(Node node) {
        StringBuilder result = new StringBuilder();
        if (node != null) {
            result.append(node.value).append(" ");
            result.append(Preorden(node.left));
            result.append(Preorden(node.right));
        }
        return result.toString();
    }
    
    public int getWidth() {
        return getWidth(root);
    }

    private int getWidth(Node node) {
        if (node == null)
            return 0;
        int leftWidth = getWidth(node.left);
        int rightWidth = getWidth(node.right);
        return Math.max(1, leftWidth + rightWidth);
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;
        int leftHeight = getHeight(node.left);
        int rightHeight = getHeight(node.right);
        return Math.max(1, Math.max(leftHeight, rightHeight) + 1);
    }
}

