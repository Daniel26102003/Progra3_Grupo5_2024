/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Esaú_Pu
 */
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MemoryInputDialog extends JDialog {
    private JTextField sizeField;
    private JComboBox<String> unitComboBox;
    private JTextField partitionsField;
    private boolean confirmed;

    public MemoryInputDialog(JFrame parent) {
        super(parent, "Ingresar Memoria", true);
        setupUI();
    }

    private void setupUI() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Tamaño:"));
        sizeField = new JTextField(10);
        sizePanel.add(sizeField);
        unitComboBox = new JComboBox<>(new String[] { "KB", "MB", "GB" });
        sizePanel.add(unitComboBox);
        add(sizePanel);
        
        JPanel partitionsPanel = new JPanel();
        partitionsPanel.add(new JLabel("Particiones:"));
        partitionsField = new JTextField(10);
        partitionsPanel.add(partitionsField);
        add(partitionsPanel);
       
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmar");
        confirmButton.addActionListener(e -> onConfirm());
        buttonPanel.add(confirmButton);
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(e -> onCancel());
        buttonPanel.add(cancelButton);
        add(buttonPanel);

        pack();
        setLocationRelativeTo(getParent());
    }

    private void onConfirm() {
        if (validateInput()) {
            confirmed = true;
            setVisible(false);
        }
    }

    private void onCancel() {
        confirmed = false;
        setVisible(false);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getMemorySize() {
        return Integer.parseInt(sizeField.getText());
    }

    public String getUnit() {
        return (String) unitComboBox.getSelectedItem();
    }

    public int getPartitions() {
        return Integer.parseInt(partitionsField.getText());
    }

    private boolean validateInput() {
        try {
            int size = Integer.parseInt(sizeField.getText());
            int partitions = Integer.parseInt(partitionsField.getText());
            if (size <= 0 || partitions <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para tamaño y particiones.", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}