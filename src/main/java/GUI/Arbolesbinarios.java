/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Clases.BinaryTree;
import Clases.BinaryTreePanel;
import Conexion.ConexionBD;
import java.awt.Dimension;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Esaú
 */
public class Arbolesbinarios extends javax.swing.JFrame {
private BinaryTree tree;
    /**
     * Creates new form Arbolesbinarios
     */
    public Arbolesbinarios() {
        initComponents();
        this.setLocationRelativeTo(null);
        cerrar();
        setVisible(true);
        mostrarDatosEnTabla();
        tree = new BinaryTree();
        txDato.setVisible(false);
        txID.setVisible(false);
        txEstado.setVisible(false);
        
    }
    public void cerrar(){
        
        try {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter(){
            
            public void windowClosing(WindowEvent e){
                
             confirmarsalida();
            }
        });
        } catch(Exception e){
            
        } 
    }
    
    public void confirmarsalida(){
        int valor=JOptionPane.showConfirmDialog(this,"¿Desea cerrar la aplicación?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE );
        if (valor==JOptionPane.YES_OPTION){
            JOptionPane.showMessageDialog(null,"Hasta pronto","",JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }      
    }
    
    public void mostrarDatosEnTabla() {
        ConexionBD cn = new ConexionBD(); 
        Connection con = cn.conexion(); 

        try {
            String sql = "SELECT * FROM arbolesbin";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("DATO");
            modelo.addColumn("ESTADO");

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getString("ID");
                fila[1] = rs.getString("DATO");
                fila[2] = rs.getString("ESTADO");
                modelo.addRow(fila);
            }
            tblBD.setModel(modelo);
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.err.println("Error al obtener los datos: " + e.getMessage());
        }
    }
    
    private void imprimirArbol() {
        boolean imprimirArbol = obtenerEstadoDesdeBD();
        if (!imprimirArbol) {
            JOptionPane.showMessageDialog(null, "No se puede graficar el árbol porque el estado es 0.", "Mensaje de Alerta", JOptionPane.WARNING_MESSAGE);
            return; 
        }
        tree = new BinaryTree();
        String input = txDato.getText();
        String[] values = input.split(",");
        for (String value : values) {
            try {
                int num = Integer.parseInt(value.trim());
                tree.insert(num);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        int internalFrameWidth = jInternalFrame1.getWidth();
        int internalFrameHeight = jInternalFrame1.getHeight();
        BinaryTreePanel panel = new BinaryTreePanel(tree);
        panel.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
        jInternalFrame1.setContentPane(panel);
        jInternalFrame1.pack();
        jInternalFrame1.setVisible(true);
    }

    private boolean obtenerEstadoDesdeBD() {
        boolean imprimirArbol = false;
        String idStr = txID.getText();
        if (!idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);

                ConexionBD cn = new ConexionBD(); 
                Connection con = cn.conexion(); 

                String sql = "SELECT ESTADO FROM arbolesbin WHERE ID = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int estado = rs.getInt("ESTADO");
                    imprimirArbol = (estado != 0);
                }

                rs.close();
                pstmt.close();
                con.close();
            } catch (NumberFormatException | SQLException e) {
                System.err.println("Error al obtener el estado: " + e.getMessage());
            }
        }
        return imprimirArbol;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBD = new javax.swing.JTable();
        btnCargaArchivoBD = new javax.swing.JButton();
        btnGenerarArbol = new javax.swing.JButton();
        btnImprimirArbol = new javax.swing.JButton();
        btnLimpiarBD = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        txDato = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txID = new javax.swing.JTextField();
        txEstado = new javax.swing.JTextField();
        btnMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblBD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblBD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBDMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tblBDMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tblBD);

        btnCargaArchivoBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cargar.png"))); // NOI18N
        btnCargaArchivoBD.setText("Cargar archivo a BD");
        btnCargaArchivoBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargaArchivoBDActionPerformed(evt);
            }
        });

        btnGenerarArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/generar.png"))); // NOI18N
        btnGenerarArbol.setText("Generar Árbol");
        btnGenerarArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarArbolActionPerformed(evt);
            }
        });

        btnImprimirArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Imprimir.png"))); // NOI18N
        btnImprimirArbol.setText("Imprimir Árbol");
        btnImprimirArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirArbolActionPerformed(evt);
            }
        });

        btnLimpiarBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/limpiar.png"))); // NOI18N
        btnLimpiarBD.setText("Limpiar Base de Datos");
        btnLimpiarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarBDActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 592, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 395, Short.MAX_VALUE)
        );

        jDesktopPane1.setLayer(jInternalFrame1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1203, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 69, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jDesktopPane1);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("Árboles Binarios");

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/regresar.png"))); // NOI18N
        btnMenu.setText("MenuPrincipal");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(btnGenerarArbol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
            .addGroup(layout.createSequentialGroup()
                .addGap(536, 536, 536)
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(btnImprimirArbol, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(173, 173, 173)
                        .addComponent(btnLimpiarBD, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(btnCargaArchivoBD)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnMenu)
                        .addGap(34, 34, 34)))
                .addGap(623, 623, 623))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txDato, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(223, 223, 223))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnMenu))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(txDato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCargaArchivoBD)
                        .addGap(16, 16, 16)
                        .addComponent(btnGenerarArbol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnImprimirArbol)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiarBD)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBDMouseClicked
        txDato.setEditable(false);
        int fila=this.tblBD.getSelectedRow();
        this.txDato.setText(this.tblBD.getValueAt(fila, 1).toString());
        this.txID.setText(this.tblBD.getValueAt(fila, 0).toString());
        this.txEstado.setText(this.tblBD.getValueAt(fila, 2).toString());
        tree = new BinaryTree();

    String input = txDato.getText();
    String[] values = input.split(",");
    for (String value : values) {
        try {
            int num = Integer.parseInt(value.trim());
            tree.insert(num);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
    }
    }//GEN-LAST:event_tblBDMouseClicked

    private void btnImprimirArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirArbolActionPerformed
        if (!txDato.getText().isEmpty()) {
        imprimirArbol();
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione al menos un valor para imprimir el árbol", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnImprimirArbolActionPerformed

    private void btnLimpiarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarBDActionPerformed
    String texto1 = txDato.getText();
    String texto2 = txID.getText();
    String texto3= txEstado.getText();
    
    if (!texto1.isEmpty() && !texto2.isEmpty() && !texto3.isEmpty()) {
        Eliminacion vEliminacion= new Eliminacion();
        vEliminacion.txID.setText(texto2);
        vEliminacion.txDATO.setText(texto1);
        vEliminacion.txESTADO.setText(texto3);
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos antes de continuar.", "Campos vacíos", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnLimpiarBDActionPerformed

    private void btnGenerarArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarArbolActionPerformed

    String postorden = "Recorrido Postorden: " + tree.Postorden();
    String inorden = "\nRecorrido Inorden: " + tree.Inorden();
    String preorden = "\nRecorrido Preorden: " + tree.Preorden();
    
    String newText = postorden + "\n" + inorden + "\n" + preorden;
    
    if (jTextArea1 == null) {
        jTextArea1 = new JTextArea();
        jTextArea1.setEditable(false);
    }
    
    jTextArea1.setText(newText);

    }//GEN-LAST:event_btnGenerarArbolActionPerformed

    private void tblBDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBDMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_tblBDMouseEntered

    private void btnCargaArchivoBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargaArchivoBDActionPerformed
        Carga vCarga =new Carga();
        dispose();
    }//GEN-LAST:event_btnCargaArchivoBDActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        MenuPrincipal vMenu =new MenuPrincipal();
        dispose();
    }//GEN-LAST:event_btnMenuActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCargaArchivoBD;
    private javax.swing.JButton btnGenerarArbol;
    private javax.swing.JButton btnImprimirArbol;
    private javax.swing.JButton btnLimpiarBD;
    private javax.swing.JButton btnMenu;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tblBD;
    private javax.swing.JTextField txDato;
    private javax.swing.JTextField txEstado;
    private javax.swing.JTextField txID;
    // End of variables declaration//GEN-END:variables
ConexionBD cn = new ConexionBD();
Connection con = cn.conexion();
}
