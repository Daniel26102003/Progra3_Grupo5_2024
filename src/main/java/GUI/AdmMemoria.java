/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Clases.MemoryInputDialog;
import Clases.MemoryPanel;
import Clases.PartitionInputDialog;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JOptionPane;


/**
 *
 * @author Esaú
 */
public class AdmMemoria extends javax.swing.JFrame {
    /**
     * Creates new form AdmMemoria
     */
    private MemoryPanel memoryPanel1;
    private MemoryPanel memoryPanel2;
    private int indexOfNewPartition = -1;
    
    public AdmMemoria() {
        initComponents();
        this.setLocationRelativeTo(null);
        setVisible(true);
        
    }
        private void applyFirstFitAlgorithm(int newPartitionSize) {
        int partitions = memoryPanel1.getPartitions();
        int[] partitionSizes = Arrays.copyOf(memoryPanel1.getPartitionSizes(), partitions);
        boolean[] isAssigned = Arrays.copyOf(memoryPanel1.getAssignedPartitions(), partitions);

        int firstFitIndex = -1;

        for (int i = 0; i < partitions; i++) {
            if (!isAssigned[i] && partitionSizes[i] >= newPartitionSize) {
                firstFitIndex = i;
                break;
            }
        }

        if (firstFitIndex == -1) {
            JOptionPane.showMessageDialog(this, "No hay suficiente espacio para la nueva partición.");
            return;
        }

        int remainingSpace = partitionSizes[firstFitIndex] - newPartitionSize;
        partitionSizes[firstFitIndex] = newPartitionSize;
        isAssigned[firstFitIndex] = true;

        indexOfNewPartition = firstFitIndex;

        if (remainingSpace > 0) {
            partitionSizes = Arrays.copyOf(partitionSizes, partitions + 1);
            isAssigned = Arrays.copyOf(isAssigned, partitions + 1);
            for (int j = partitions; j > firstFitIndex + 1; j--) {
                partitionSizes[j] = partitionSizes[j - 1];
                isAssigned[j] = isAssigned[j - 1];
            }
            partitionSizes[firstFitIndex + 1] = remainingSpace;
            isAssigned[firstFitIndex + 1] = false;
        }

        MemoryPanel assignedMemoryPanel = new MemoryPanel(memoryPanel1.getMemorySize(), partitions + 1, memoryPanel1.getUnit(), partitionSizes, isAssigned);
        setMemoryPanelContent(assignedMemoryPanel, JInternalFrame2);
        assignedMemoryPanel.setIndexOfNewPartition(indexOfNewPartition);
    }
        
   private void applyBestFitAlgorithm(int newPartitionSize) {
        int partitions = memoryPanel1.getPartitions();
        int[] partitionSizes = Arrays.copyOf(memoryPanel1.getPartitionSizes(), partitions);
        boolean[] isAssigned = Arrays.copyOf(memoryPanel1.getAssignedPartitions(), partitions);

        int bestIndex = -1;
        int smallestFit = Integer.MAX_VALUE;

        for (int i = 0; i < partitions; i++) {
            int space = partitionSizes[i] - newPartitionSize;
            if (space >= 0 && space < smallestFit && !isAssigned[i]) {
                bestIndex = i;
                smallestFit = space;
            }
        }

        if (bestIndex == -1) {
            JOptionPane.showMessageDialog(this, "No hay suficiente espacio para la nueva partición.");
            return;
        }

        int remainingSpace = partitionSizes[bestIndex] - newPartitionSize;
        partitionSizes[bestIndex] = newPartitionSize;
        isAssigned[bestIndex] = true;

        indexOfNewPartition = bestIndex;

        if (remainingSpace > 0) {
            partitionSizes = Arrays.copyOf(partitionSizes, partitions + 1);
            isAssigned = Arrays.copyOf(isAssigned, partitions + 1);
            for (int j = partitions; j > bestIndex + 1; j--) {
                partitionSizes[j] = partitionSizes[j - 1];
                isAssigned[j] = isAssigned[j - 1];
            }
            partitionSizes[bestIndex + 1] = remainingSpace;
            isAssigned[bestIndex + 1] = false;
        }

        MemoryPanel assignedMemoryPanel = new MemoryPanel(memoryPanel1.getMemorySize(), partitions + 1, memoryPanel1.getUnit(), partitionSizes, isAssigned);
        setMemoryPanelContent(assignedMemoryPanel, JInternalFrame2);
        assignedMemoryPanel.setIndexOfNewPartition(indexOfNewPartition);
    }
        
    private void applyWorstFitAlgorithm(int newPartitionSize) {
        int partitions = memoryPanel1.getPartitions();
        int[] partitionSizes = Arrays.copyOf(memoryPanel1.getPartitionSizes(), partitions);
        boolean[] isAssigned = Arrays.copyOf(memoryPanel1.getAssignedPartitions(), partitions);

        int worstIndex = -1;
        int largestFit = -1;

        for (int i = 0; i < partitions; i++) {
            int space = partitionSizes[i] - newPartitionSize;
            if (space >= 0 && space > largestFit && !isAssigned[i]) {
                worstIndex = i;
                largestFit = space;
            }
        }

        if (worstIndex == -1) {
            JOptionPane.showMessageDialog(this, "No hay suficiente espacio para la nueva partición.");
            return;
        }

        int remainingSpace = partitionSizes[worstIndex] - newPartitionSize;
        partitionSizes[worstIndex] = newPartitionSize;
        isAssigned[worstIndex] = true;

        indexOfNewPartition = worstIndex;

        if (remainingSpace > 0) {
            partitionSizes = Arrays.copyOf(partitionSizes, partitions + 1);
            isAssigned = Arrays.copyOf(isAssigned, partitions + 1);
            for (int j = partitions; j > worstIndex + 1; j--) {
                partitionSizes[j] = partitionSizes[j - 1];
                isAssigned[j] = isAssigned[j - 1];
            }
            partitionSizes[worstIndex + 1] = remainingSpace;
            isAssigned[worstIndex + 1] = false;
        }

        MemoryPanel assignedMemoryPanel = new MemoryPanel(memoryPanel1.getMemorySize(), partitions + 1, memoryPanel1.getUnit(), partitionSizes, isAssigned);
        setMemoryPanelContent(assignedMemoryPanel, JInternalFrame2);
        assignedMemoryPanel.setIndexOfNewPartition(indexOfNewPartition);
    }

    private void setMemoryPanelContent(MemoryPanel memoryPanel, javax.swing.JInternalFrame frame) {
        frame.setContentPane(memoryPanel);
        frame.pack();
    }
     
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        JInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        JInternalFrame2 = new javax.swing.JInternalFrame();
        btnIngresar = new javax.swing.JButton();
        btnPrimerajuste = new javax.swing.JButton();
        btnMejorajuste = new javax.swing.JButton();
        btnPeorajuste = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        jLabel1.setText("Administrador de Memoria");

        JInternalFrame1.setVisible(true);

        javax.swing.GroupLayout JInternalFrame1Layout = new javax.swing.GroupLayout(JInternalFrame1.getContentPane());
        JInternalFrame1.getContentPane().setLayout(JInternalFrame1Layout);
        JInternalFrame1Layout.setHorizontalGroup(
            JInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 486, Short.MAX_VALUE)
        );
        JInternalFrame1Layout.setVerticalGroup(
            JInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );

        jDesktopPane1.setLayer(JInternalFrame1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JInternalFrame1)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JInternalFrame1)
        );

        jScrollPane1.setViewportView(jDesktopPane1);

        jLabel2.setText("Memoria:");

        JInternalFrame2.setVisible(true);

        javax.swing.GroupLayout JInternalFrame2Layout = new javax.swing.GroupLayout(JInternalFrame2.getContentPane());
        JInternalFrame2.getContentPane().setLayout(JInternalFrame2Layout);
        JInternalFrame2Layout.setHorizontalGroup(
            JInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 487, Short.MAX_VALUE)
        );
        JInternalFrame2Layout.setVerticalGroup(
            JInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
        );

        jDesktopPane2.setLayer(JInternalFrame2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
        jDesktopPane2.setLayout(jDesktopPane2Layout);
        jDesktopPane2Layout.setHorizontalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JInternalFrame2)
        );
        jDesktopPane2Layout.setVerticalGroup(
            jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JInternalFrame2)
        );

        jScrollPane2.setViewportView(jDesktopPane2);

        btnIngresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ingresar.png"))); // NOI18N
        btnIngresar.setText("Ingresar Memoria");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnPrimerajuste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Primer ajuste.png"))); // NOI18N
        btnPrimerajuste.setText("Primer Ajuste");
        btnPrimerajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimerajusteActionPerformed(evt);
            }
        });

        btnMejorajuste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ajuste.png"))); // NOI18N
        btnMejorajuste.setText("Mejor Ajuste");
        btnMejorajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMejorajusteActionPerformed(evt);
            }
        });

        btnPeorajuste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/voto-negativo.png"))); // NOI18N
        btnPeorajuste.setText("Peor Ajuste");
        btnPeorajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeorajusteActionPerformed(evt);
            }
        });

        jLabel3.setText("Memoria con configuración:");

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/regresar.png"))); // NOI18N
        btnMenu.setText("Menú Principal");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(333, 333, 333))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(412, 412, 412)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(btnIngresar)
                .addGap(53, 53, 53)
                .addComponent(btnMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrimerajuste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMejorajuste)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPeorajuste)
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresar)
                    .addComponent(btnPrimerajuste)
                    .addComponent(btnMejorajuste)
                    .addComponent(btnPeorajuste)
                    .addComponent(btnMenu))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPeorajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeorajusteActionPerformed
       PartitionInputDialog dialog = new PartitionInputDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
        int newSize = dialog.getPartitionSize();
        applyWorstFitAlgorithm(newSize);
    }
    }//GEN-LAST:event_btnPeorajusteActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
       MemoryInputDialog dialog = new MemoryInputDialog(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            int size = dialog.getMemorySize();
            String unit = dialog.getUnit();
            int partitions = dialog.getPartitions();

            memoryPanel1 = new MemoryPanel(size, partitions, unit);
            setMemoryPanelContent(memoryPanel1, JInternalFrame1);
        }
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnMejorajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMejorajusteActionPerformed
         PartitionInputDialog dialog = new PartitionInputDialog(this);
        dialog.setVisible(true);

    if (dialog.isConfirmed()) {
        int newSize = dialog.getPartitionSize();
        applyBestFitAlgorithm(newSize);
    }
    }//GEN-LAST:event_btnMejorajusteActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        MenuPrincipal vMenu = new MenuPrincipal();
        dispose();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnPrimerajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimerajusteActionPerformed
        PartitionInputDialog dialog = new PartitionInputDialog(this);
        dialog.setVisible(true);

    if (dialog.isConfirmed()) {
        int newSize = dialog.getPartitionSize();
        applyFirstFitAlgorithm(newSize);
    }
    }//GEN-LAST:event_btnPrimerajusteActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JInternalFrame JInternalFrame1;
    private javax.swing.JInternalFrame JInternalFrame2;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnMejorajuste;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnPeorajuste;
    private javax.swing.JButton btnPrimerajuste;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JDesktopPane jDesktopPane2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
