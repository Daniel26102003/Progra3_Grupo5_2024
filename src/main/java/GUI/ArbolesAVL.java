/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Clases.AVLTree;
import Clases.AVLTreePanel;
import Conexion.ConexionBD;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Esaú
 */
public class ArbolesAVL extends javax.swing.JFrame {
    private AVLTree miArbol;
    private AVLTreePanel miPanel;
    private int idTipoArbol = -1;
    private int orden = 0;
    private int cantidadNodos = 0;
    
    
    ArbolesAVL() {
        
    initComponents(); 
    setVisible(true);
    setLocationRelativeTo(null);
    cerrar();
    miArbol = new AVLTree();
    miPanel = new AVLTreePanel(miArbol);
    mostrarDatosTipoArbol();
    txDato.setVisible(false);
    txID.setVisible(false);
}

    public void cerrar() {
        try {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    confirmarsalida();
                }
            });
        } catch (Exception e) {
        }
    }

    public void confirmarsalida() {
        int valor = JOptionPane.showConfirmDialog(this, "¿Desea cerrar la aplicación?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (valor == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Hasta pronto", "", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
    private void nuevoArbol() {
        miArbol = new AVLTree();
        miPanel = new AVLTreePanel(miArbol);
        int internalFrameWidth = jInternalFrame1.getWidth();
        int internalFrameHeight = jInternalFrame1.getHeight();
        miPanel.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
        jInternalFrame1.setContentPane(miPanel);
        jInternalFrame1.pack();
        jInternalFrame1.setVisible(true);
        idTipoArbol = -1; 
    }
    
    public void mostrarDatosTipoArbol() {
    ConexionBD cn = new ConexionBD(); 
    Connection con = cn.conexion(); 

    try {
        String sql = "SELECT t.IDTIPOARBOL, t.NOMBRE, " +
                     "GROUP_CONCAT(a.Dato ORDER BY a.ID ASC SEPARATOR ',') AS Datos, " +
                     "t.ESTADO " +
                     "FROM tipoarbol t " +
                     "JOIN arbolesavl a ON t.IDTIPOARBOL = a.IDTIPOARBOL " +
                     "WHERE t.NOMBRE = 'Arbol AVL' " +
                     "GROUP BY t.IDTIPOARBOL";
                     
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Tipo Árbol");
        modelo.addColumn("Nombre");
        modelo.addColumn("Datos");
        modelo.addColumn("Estado");

        while (rs.next()) {
            Object[] fila = new Object[4];
            fila[0] = rs.getString("IDTIPOARBOL");
            fila[1] = rs.getString("NOMBRE");
            fila[2] = rs.getString("Datos");
            fila[3] = rs.getString("ESTADO");
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
    
   
    private void imprimirArbol(AVLTree tree) {
    if (miPanel == null) {
        miPanel = new AVLTreePanel(tree);
        miPanel.setPreferredSize(new Dimension(800, 600));
        jInternalFrame1.setContentPane(miPanel);
        jInternalFrame1.pack();
        jInternalFrame1.setVisible(true);
    } else {
        miPanel.repaint();
    }
}
    private void eliminarNodo(AVLTree tree, int num) {
    tree.deleteNode(num);
    miPanel.repaint();
}
    
    private void imprimirArbol2() {
    boolean imprimirArbol2 = obtenerEstadoDesdeBD();
    if (!imprimirArbol2) {
        JOptionPane.showMessageDialog(null, "No se puede graficar el árbol porque el estado es 0.", "Mensaje de Alerta", JOptionPane.WARNING_MESSAGE);
        return; 
    }
    
    String inputValue = txDato.getText();
    if (inputValue == null || inputValue.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese al menos un número.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String[] values = inputValue.split(",");
    AVLTree tree = miPanel.getTree();
    
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
    miPanel.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
    jInternalFrame1.setContentPane(miPanel);
    jInternalFrame1.pack();
    jInternalFrame1.setVisible(true);
} 

    private boolean obtenerEstadoDesdeBD() {
        boolean imprimirArbol2 = false;
        String idStr = txID.getText();
        if (!idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);

                ConexionBD cn = new ConexionBD(); 
                Connection con = cn.conexion(); 

                String sql = "SELECT ESTADO FROM arbolesavl WHERE IDTIPOARBOL = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setInt(1, id);

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int estado = rs.getInt("ESTADO");
                    imprimirArbol2 = (estado != 0);
                }

                rs.close();
                pstmt.close();
                con.close();
            } catch (NumberFormatException | SQLException e) {
                System.err.println("Error al obtener el estado: " + e.getMessage());
            }
        }
        return imprimirArbol2;
    }

    private void imprimirArbol() {
        String inputValue = JOptionPane.showInputDialog("Ingrese un número para agregar al árbol:");
        try {
            int num = Integer.parseInt(inputValue.trim());
            miPanel.getTree().insert(num);
            int internalFrameWidth = jInternalFrame1.getWidth();
            int internalFrameHeight = jInternalFrame1.getHeight();
            miPanel.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            jInternalFrame1.setContentPane(miPanel);
            jInternalFrame1.pack();
            jInternalFrame1.setVisible(true);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void eliminarNodo() {
        String inputValue = JOptionPane.showInputDialog("Ingrese el número que desea eliminar:");
        try {
            int num = Integer.parseInt(inputValue.trim());
            miPanel.getTree().deleteNode(num);
            int internalFrameWidth = jInternalFrame1.getWidth();
            int internalFrameHeight = jInternalFrame1.getHeight();
            miPanel.setPreferredSize(new Dimension(internalFrameWidth, internalFrameHeight));
            jInternalFrame1.setContentPane(miPanel);
            jInternalFrame1.pack();
            jInternalFrame1.setVisible(true);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private int insertarTipoArbol() {
            ConexionBD cn = new ConexionBD();
            Connection con = cn.conexion();
            try {
                PreparedStatement pst = con.prepareStatement("INSERT INTO tipoarbol (NOMBRE, ESTADO) VALUES ('ARBOL AVL', 1)", Statement.RETURN_GENERATED_KEYS);
                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    idTipoArbol = rs.getInt(1);
                }
                System.out.println("Tipo de árbol insertado en tipoarbol");
            } catch (SQLException ex) {
                System.out.println("Error al insertar tipo de árbol en tipoarbol: " + ex.getMessage());
            }
            return idTipoArbol;
        }

    private void insertarNodoArbol(int dato, int idTipoArbol, int orden) {
        ConexionBD cn = new ConexionBD();
        Connection con = cn.conexion();
        try {
            String query = "INSERT INTO arbolesavl (Dato, ESTADO, IDTIPOARBOL) VALUES (?, 1, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setInt(1, dato);
            pst.setInt(2, idTipoArbol);
            pst.executeUpdate();
            System.out.println("Nodo insertado en arbolesavl");
        } catch (SQLException ex) {
            System.out.println("Error al insertar nodo en arbolesavl: " + ex.getMessage());
        }
    }

    private void guardarArbolEnBD(AVLTree.Node nodo, int idTipoArbol, int orden) {
        if (nodo != null) {
            insertarNodoArbol(nodo.value, idTipoArbol, orden);
            guardarArbolEnBD(nodo.left, idTipoArbol, orden + 1);
            guardarArbolEnBD(nodo.right, idTipoArbol, orden + 1);
        }
    }
    
    private void actualizarEstado() {
        String sqlTipoArbol = "UPDATE tipoarbol SET ESTADO = 0 WHERE IDTIPOARBOL = ?";
        String sqlArbolesAVL = "UPDATE arbolesavl SET ESTADO = 0 WHERE IDTIPOARBOL = ?";

        try { 

            int idTipoArbol = Integer.parseInt(txID.getText());

            PreparedStatement psTipoArbol = con.prepareStatement(sqlTipoArbol);
            psTipoArbol.setInt(1, idTipoArbol);
            psTipoArbol.executeUpdate();

            PreparedStatement psArbolesAVL = con.prepareStatement(sqlArbolesAVL);
            psArbolesAVL.setInt(1, idTipoArbol);
            psArbolesAVL.executeUpdate();

            con.close();
            System.out.println("Estado actualizado correctamente.");
        } catch (SQLException | NumberFormatException ex) {
            System.err.println("Error al actualizar el estado: " + ex.getMessage());
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblBD = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        btnMenu = new javax.swing.JButton();
        btnInsertar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        txDato = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        btnLimpiarBD = new javax.swing.JButton();
        txID = new javax.swing.JTextField();

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
        });
        jScrollPane1.setViewportView(tblBD);

        jLabel1.setFont(new java.awt.Font("Arial Black", 0, 18)); // NOI18N
        jLabel1.setText("Arboles AVL");

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 677, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        jDesktopPane1.setLayer(jInternalFrame1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jInternalFrame1)
                .addContainerGap())
        );

        jScrollPane2.setViewportView(jDesktopPane1);

        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/regresar.png"))); // NOI18N
        btnMenu.setText("Menú Principal");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        btnInsertar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Imprimir.png"))); // NOI18N
        btnInsertar.setText("Insertar Nodo");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Eliminar Nodo.png"))); // NOI18N
        btnEliminar.setText("Eliminar Nodo");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cargar.png"))); // NOI18N
        btnGuardar.setText("Guardar AVL a BD");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Imprimir.png"))); // NOI18N
        btnImprimir.setText("Imprimir Arbol");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Limpiar Graficos.png"))); // NOI18N
        btnLimpiar.setText("Limpiar Graficos");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnLimpiarBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/limpiar.png"))); // NOI18N
        btnLimpiarBD.setText("Limpiar BD");
        btnLimpiarBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarBDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btnInsertar)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnLimpiarBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)
                        .addGap(41, 41, 41)
                        .addComponent(btnImprimir)
                        .addGap(46, 46, 46)
                        .addComponent(btnMenu)
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txDato, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(273, 273, 273))))
            .addGroup(layout.createSequentialGroup()
                .addGap(529, 529, 529)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnLimpiar)
                                .addComponent(btnImprimir)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txDato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnEliminar)
                            .addComponent(btnInsertar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpiarBD)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        MenuPrincipal vMenu = new MenuPrincipal();
        dispose();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        imprimirArbol();
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
       nuevoArbol();
    if (!txDato.getText().isEmpty()) {
        imprimirArbol2();
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione al menos un valor para imprimir el árbol", "Advertencia", JOptionPane.WARNING_MESSAGE);
    }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void tblBDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBDMouseClicked
        // TODO add your handling code here:
        txDato.setEditable(false);
        int fila=this.tblBD.getSelectedRow();
        this.txDato.setText(this.tblBD.getValueAt(fila, 2).toString());
        this.txID.setText(this.tblBD.getValueAt(fila, 0).toString());
        AVLTree tree = new AVLTree();

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

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        nuevoArbol();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarNodo();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (idTipoArbol == -1) {
            idTipoArbol = insertarTipoArbol();
        }
        if (idTipoArbol != -1) {
            guardarArbolEnBD(miArbol.getRoot(), idTipoArbol, orden);
            JOptionPane.showMessageDialog(null, "Árbol guardado en la base de datos.", "Información", JOptionPane.INFORMATION_MESSAGE);
            nuevoArbol();
            orden = 0;
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el árbol en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        mostrarDatosTipoArbol();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarBDActionPerformed
        // TODO add your handling code here:
        actualizarEstado();
        mostrarDatosTipoArbol();
    }//GEN-LAST:event_btnLimpiarBDActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiarBD;
    private javax.swing.JButton btnMenu;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblBD;
    private javax.swing.JTextField txDato;
    private javax.swing.JTextField txID;
    // End of variables declaration//GEN-END:variables
ConexionBD cn = new ConexionBD();
Connection con = cn.conexion();
}
