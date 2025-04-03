/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;
/**
 *
 * @author hp
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.EventObject;
import modelo.HojaCalculo;
import modelo.Celda;
import controlador.Controlador;

public class InterfazGraficaHojaCalculo extends JFrame{
    
    // Atributos de la interfaz gráfica
    private HojaCalculo hoja;
    private Controlador controlador;
    private JTable tabla;
    private ModeloTablaHojaCalculo modeloTabla;
    private JTextField barraDireccion;
    private JTextField barraFormula;
    private int filaSeleccionada = 0;
    private int columnaSeleccionada = 0;
    //private DialogoAyuda dialogoAyuda;
    
    /**
     * Constructor de la interfaz gráfica
     */
    public InterfazGraficaHojaCalculo() {
        hoja = new HojaCalculo();
        inicializarComponentes();
        configurarEventos();
        setTitle("Hoja de Cálculo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear el diálogo de ayuda
        dialogoAyuda = new DialogoAyuda(this);
    }
    
    //Establece el controlador para la vista
     
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    
    //Inicializa los componentes de la interfaz gráfica
     
    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        
        // Barra de herramientas principal
        JToolBar barraHerramientas = new JToolBar();
        barraHerramientas.setFloatable(false);
        
        // Botones de la barra de herramientas
        JButton btnAyuda = new JButton("Ayuda");
        JButton btnCerrar = new JButton("Cerrar");
        
        // Configurar tooltips
        btnAyuda.setToolTipText("Mostrar ayuda");
        btnCerrar.setToolTipText("Cerrar la aplicación");
        
        // Configurar eventos para los botones
        btnAyuda.addActionListener(e -> mostrarAyuda());
        btnCerrar.addActionListener(e -> System.exit(0));
        
        barraHerramientas.add(btnAyuda);
        barraHerramientas.addSeparator();
        barraHerramientas.add(btnCerrar);
        
        // Panel para las barras de dirección y fórmula
        JPanel panelBarras = new JPanel(new BorderLayout());
        panelBarras.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        // Panel de dirección
        JPanel panelDireccion = new JPanel(new BorderLayout());
        JLabel lblDireccion = new JLabel("Celda: ");
        barraDireccion = new JTextField(5);
        barraDireccion.setEditable(false);
        panelDireccion.add(lblDireccion, BorderLayout.WEST);
        panelDireccion.add(barraDireccion, BorderLayout.CENTER);
        
        // Panel de fórmula con botones
        JPanel panelFormula = new JPanel(new BorderLayout());
        panelFormula.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        // Etiqueta de fórmula
        JLabel lblFormula = new JLabel("Fórmula: ");
        
        // Panel para los botones de fórmula
        JPanel panelBotonesFormula = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        
        // Botones para operaciones matemáticas
        JButton btnIgual = new JButton("=");
        JButton btnSuma = new JButton("+");
        JButton btnResta = new JButton("-");
        JButton btnMultiplicacion = new JButton("*");
        JButton btnDivision = new JButton("/");
        JButton btnSumarRango = new JButton("Sumar");
        JButton btnParentesisIzq = new JButton("(");
        JButton btnParentesisDer = new JButton(")");
        
        // Configurar tamaño de botones
        Dimension tamanoBoton = new Dimension(50, 25);
        btnIgual.setPreferredSize(tamanoBoton);
        btnSuma.setPreferredSize(tamanoBoton);
        btnResta.setPreferredSize(tamanoBoton);
        btnMultiplicacion.setPreferredSize(tamanoBoton);
        btnDivision.setPreferredSize(tamanoBoton);
        btnParentesisIzq.setPreferredSize(tamanoBoton);
        btnParentesisDer.setPreferredSize(tamanoBoton);
        btnSumarRango.setPreferredSize(new Dimension(70, 25));
        
        // Configurar tooltips
        btnIgual.setToolTipText("Iniciar fórmula");
        btnSuma.setToolTipText("Suma");
        btnResta.setToolTipText("Resta");
        btnMultiplicacion.setToolTipText("Multiplicación");
        btnDivision.setToolTipText("División");
        btnParentesisIzq.setToolTipText("Paréntesis izquierdo");
        btnParentesisDer.setToolTipText("Paréntesis derecho");
        btnSumarRango.setToolTipText("Sumar un rango de celdas (ej: Sumar(A1:A5))");
        
        // Agregar botones al panel
        panelBotonesFormula.add(btnIgual);
        panelBotonesFormula.add(btnSuma);
        panelBotonesFormula.add(btnResta);
        panelBotonesFormula.add(btnMultiplicacion);
        panelBotonesFormula.add(btnDivision);
        panelBotonesFormula.add(btnParentesisIzq);
        panelBotonesFormula.add(btnParentesisDer);
        panelBotonesFormula.add(btnSumarRango);
        
        // Campo de texto para la fórmula
        barraFormula = new JTextField();
        
        // Configurar eventos para los botones de operaciones
        btnIgual.addActionListener(e -> insertarEnBarraFormula("="));
        btnSuma.addActionListener(e -> insertarEnBarraFormula("+"));
        btnResta.addActionListener(e -> insertarEnBarraFormula("-"));
        btnMultiplicacion.addActionListener(e -> insertarEnBarraFormula("*"));
        btnDivision.addActionListener(e -> insertarEnBarraFormula("/"));
        btnParentesisIzq.addActionListener(e -> insertarEnBarraFormula("("));
        btnParentesisDer.addActionListener(e -> insertarEnBarraFormula(")"));
        btnSumarRango.addActionListener(e -> {
            String celdaActual = hoja.convertirCoordenadaAReferencia(filaSeleccionada, columnaSeleccionada);
            insertarEnBarraFormula("=Sumar(" + celdaActual + ":");
        });
        
        // Panel para la etiqueta y los botones
        JPanel panelEtiquetaBotones = new JPanel(new BorderLayout());
        panelEtiquetaBotones.add(lblFormula, BorderLayout.WEST);
        panelEtiquetaBotones.add(panelBotonesFormula, BorderLayout.CENTER);
        
        // Agregar componentes al panel de fórmula
        panelFormula.add(panelEtiquetaBotones, BorderLayout.NORTH);
        panelFormula.add(barraFormula, BorderLayout.CENTER);
        
        // Agregar paneles a la barra principal
        panelBarras.add(panelDireccion, BorderLayout.NORTH);
        panelBarras.add(panelFormula, BorderLayout.SOUTH);
        
        // Tabla
        modeloTabla = new ModeloTablaHojaCalculo();
        tabla = new JTable(modeloTabla);
        tabla.setDefaultRenderer(Object.class, new RenderizadorCelda());
        tabla.setDefaultEditor(Object.class, new EditorCelda());
        tabla.setRowHeight(25);
        tabla.setCellSelectionEnabled(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Configurar encabezados de columna (A, B, C, ...)
        JTableHeader header = tabla.getTableHeader();
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);
        
        // Configurar encabezados de fila (1, 2, 3, ...)
        tabla.setRowSelectionAllowed(true);
        tabla.setColumnSelectionAllowed(true);
        
        // Scroll pane para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Panel superior que contiene la barra de herramientas y las barras de dirección/fórmula
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(barraHerramientas, BorderLayout.NORTH);
        panelSuperior.add(panelBarras, BorderLayout.SOUTH);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        
        // Agregar panel principal al frame
        add(panelPrincipal);
        
        // Inicializar la barra de dirección
        actualizarBarraDireccion();
    }
    
    //Muestra el diálogo de ayuda
  
    private void mostrarAyuda() {
        dialogoAyuda.setVisible(true);
    }
    
    //Inserta texto en la barra de fórmula en la posición actual del cursor
    
    private void insertarEnBarraFormula(String texto) {
        String valorActual = barraFormula.getText();
        int posicion = barraFormula.getCaretPosition();
        
        // Si es el signo igual y ya hay uno, no agregar otro
        if (texto.equals("=") && valorActual.contains("=")) {
            return;
        }
        
        // Insertar el texto en la posición actual
        String nuevoValor = valorActual.substring(0, posicion) + texto + valorActual.substring(posicion);
        barraFormula.setText(nuevoValor);
        barraFormula.setCaretPosition(posicion + texto.length());
        barraFormula.requestFocus();
    }
    
    //Configura los eventos de la interfaz gráfica
     
    private void configurarEventos() {
        // Evento para la selección de celdas
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarSeleccion();
            }
        });
        
        tabla.getColumnModel().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarSeleccion();
            }
        });
        
        // Evento para la barra de fórmula
        barraFormula.addActionListener(e -> {
            String valor = barraFormula.getText();
            if (valor.startsWith("=")) {
                // Es una fórmula, guardar como está
                hoja.establecerValor(filaSeleccionada, columnaSeleccionada, valor);
            } else {
                // Es un valor normal
                hoja.establecerValor(filaSeleccionada, columnaSeleccionada, valor);
            }
            modeloTabla.fireTableCellUpdated(filaSeleccionada, columnaSeleccionada);
            // Mover a la siguiente celda (abajo)
            if (filaSeleccionada < tabla.getRowCount() - 1) {
                tabla.setRowSelectionInterval(filaSeleccionada + 1, filaSeleccionada + 1);
                tabla.setColumnSelectionInterval(columnaSeleccionada, columnaSeleccionada);
            }
        });
        
        // Evento para doble clic en una celda
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    barraFormula.requestFocus();
                }
            }
        });
        
        // Evento para las teclas de flecha
        tabla.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_TAB:
                    case KeyEvent.VK_ENTER:
                        // Estos eventos son manejados por JTable
                        break;
                    case KeyEvent.VK_DELETE:
                        // Eliminar el contenido de la celda seleccionada
                        hoja.eliminarCelda(filaSeleccionada, columnaSeleccionada);
                        modeloTabla.fireTableCellUpdated(filaSeleccionada, columnaSeleccionada);
                        barraFormula.setText("");
                        break;
                }
            }
        });
        
        // Evento para insertar referencia de celda al hacer clic mientras se edita una fórmula
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (barraFormula.hasFocus() && barraFormula.getText().startsWith("=")) {
                    int fila = tabla.rowAtPoint(e.getPoint());
                    int columna = tabla.columnAtPoint(e.getPoint());
                    if (fila >= 0 && columna >= 0) {
                        String referencia = hoja.convertirCoordenadaAReferencia(fila, columna);
                        insertarEnBarraFormula(referencia);
                    }
                }
            }
        });
    }
    
    //Actualiza la selección actual de la celda
 
    private void actualizarSeleccion() {
        int fila = tabla.getSelectedRow();
        int columna = tabla.getSelectedColumn();
        
        if (fila >= 0 && columna >= 0) {
            filaSeleccionada = fila;
            columnaSeleccionada = columna;
            actualizarBarraDireccion();
            actualizarBarraFormula();
        }
    }
    
    //Actualiza la barra de dirección con la referencia de la celda seleccionada
    
    private void actualizarBarraDireccion() {
        String referencia = hoja.convertirCoordenadaAReferencia(filaSeleccionada, columnaSeleccionada);
        barraDireccion.setText(referencia);
    }
    
    //Actualiza la barra de fórmula con el valor de la celda seleccionada
     
    private void actualizarBarraFormula() {
        Celda celda = hoja.obtenerCelda(filaSeleccionada, columnaSeleccionada);
        if (celda != null) {
            barraFormula.setText(celda.getValor());
        } else {
            barraFormula.setText("");
        }
    }
    
    //Modelo de tabla personalizado para la hoja de cálculo
     
    private class ModeloTablaHojaCalculo extends AbstractTableModel {
        private static final int NUM_FILAS = 100;
        private static final int NUM_COLUMNAS = 26; // A-Z
        
        public int getRowCount() {
            return NUM_FILAS;
        }
        
        public int getColumnCount() {
            return NUM_COLUMNAS;
        }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
            Celda celda = hoja.obtenerCelda(rowIndex, columnIndex);
            if (celda != null) {
                String valor = celda.getValor();
                // Si es una fórmula, evaluar y mostrar el resultado
                if (valor != null && valor.startsWith("=")) {
                    try {
                        double resultado = hoja.evaluarFormula(valor);
                        return resultado;
                    } catch (Exception e) {
                        return "#ERROR";
                    }
                }
                return valor;
            }
            return null;
        }
        
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }
        
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (aValue != null) {
                String valor = aValue.toString();
                hoja.establecerValor(rowIndex, columnIndex, valor);
                fireTableCellUpdated(rowIndex, columnIndex);
            }
        }
        
        public String getColumnName(int column) {
            // Convertir número de columna a letra (0=A, 1=B, ...)
            return String.valueOf((char) ('A' + column));
        }
    }
    
    // Renderizador personalizado para las celdas de la tabla
     
    private class RenderizadorCelda extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, 
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);
            
            // Personalizar apariencia
            if (isSelected) {
                c.setBackground(new Color(184, 207, 229));
            } else {
                c.setBackground(Color.WHITE);
            }
            
            return c;
        }
    }
    
    //Editor personalizado para las celdas de la tabla
    
    private class EditorCelda extends DefaultCellEditor {
        public EditorCelda() {
            super(new JTextField());
        }
        
        public boolean isCellEditable(EventObject e) {
            // Permitir edición solo con doble clic o F2
            if (e instanceof MouseEvent) {
                return ((MouseEvent) e).getClickCount() >= 2;
            }
            return super.isCellEditable(e);
        }
        
        public Component getTableCellEditorComponent(JTable table, Object value, 
                boolean isSelected, int row, int column) {
            
            // Obtener el valor real de la celda (no el valor mostrado)
            Celda celda = hoja.obtenerCelda(row, column);
            String valorReal = celda != null ? celda.getValor() : "";
            
            return super.getTableCellEditorComponent(table, valorReal, isSelected, row, column);
        }
    }
    
}
