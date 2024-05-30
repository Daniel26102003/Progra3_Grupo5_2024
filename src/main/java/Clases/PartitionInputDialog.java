/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author danie
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PartitionInputDialog extends JDialog {
    private boolean confirmed = false;
    private int partitionSize;

    private JTextField txtPartitionSize;
    private JButton btnOk;
    private JButton btnCancel;

    public PartitionInputDialog(Frame parent) {
        super(parent, "Agregar Partición", true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        txtPartitionSize = new JTextField(10);
        btnOk = new JButton("OK");
        btnCancel = new JButton("Cancelar");

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = true;
                partitionSize = Integer.parseInt(txtPartitionSize.getText());
                setVisible(false);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmed = false;
                setVisible(false);
            }
        });

        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Tamaño de la Partición:"));
        panel.add(txtPartitionSize);
        panel.add(btnOk);
        panel.add(btnCancel);
        add(panel, BorderLayout.CENTER);
        pack();
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getPartitionSize() {
        return partitionSize;
    }
}