package codigo;

import codigo.Sintax;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author juana
 */
public class CompilerComp extends javax.swing.JFrame {

    public CompilerComp() {
        UIManager.put("TabbedPane.contentBorderInsets", new InsetsUIResource(1, 0,
                0, 0));
        UIManager.put("TabbedPane.contentAreaColor", new ColorUIResource(51, 51, 61));
        UIManager.put("TabbedPane.focus", new ColorUIResource(51, 51, 61));
        UIManager.put("TabbedPane.selected", new ColorUIResource(51, 51, 61));
        UIManager.put("TabbedPane.darkShadow", new ColorUIResource(51, 51, 61));
        UIManager.put("TabbedPane.borderHightlightColor", new ColorUIResource(51, 51, 61));
        UIManager.put("TabbedPane.light", new ColorUIResource(Color.WHITE));
        UIManager.put("TabbedPane.tabAreaBackground", new ColorUIResource(51, 51, 61));
        UIManager.put("ToolTip.background", Color.WHITE);
        UIManager.put("ToolTip.border", new BorderUIResource(new LineBorder(
                Color.BLACK)));

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        try {
            setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Graphics/logo.png")).getImage());
        } catch (Exception exp) {
        }
        lblLines.setText(numLineas + " lineas");
        numeroLine.setVisible(false);
        scrollNumeroLine.setVisible(false);
        NumeroLinea numLinea;
        numLinea = new NumeroLinea(codeArea);
        scrollCodeArea.setRowHeaderView(numLinea);
        Document doc = codeArea.getDocument();
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                lineNumbers();
                colors();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                lineNumbers();
                colors();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lineNumbers();
                colors();
            }

        });

        codeArea.requestFocus();
        ini();
        addTabs();
        scrollNumeroLine.setWheelScrollingEnabled(false);
        scrollNumeroLine.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                scrollCodeArea.dispatchEvent(e);
            }
        });
        tblComps.setShowGrid(false);
        tblComps.setIntercellSpacing(new Dimension(0, 0));
        tblComps.setOpaque(false);
        ((DefaultTableCellRenderer) tblComps.getDefaultRenderer(Object.class)).setOpaque(false);
        scrollPaneComp.setOpaque(false);
        scrollPaneComp.getViewport().setOpaque(false);
        JTableHeader header = tblComps.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(51, 51, 61));
        header.setForeground(Color.WHITE);
        jtabComp.setOpaque(true);

    }

    void recuperartokens() {
        Flex flex = new Flex();
        Object o[];
        o = (Object[]) flex.recuperartokens(codeArea.getText());
        if (o == null) {
            System.err.println("El objeto fue null en: recuperartokens");
            return;
        }
        linea = (ArrayList) o[0];
        token = (ArrayList) o[1];
        lexema = (ArrayList) o[2];
        error = (ArrayList) o[3];
        p1.textPane.setText((String) o[4]);

    }

    void analisisSintacttico() {
        String texto = codeArea.getText();
        if (texto.isEmpty()) {
            System.err.println("No es posible evaluar una cadena en blanco.");
            return;
        }
        try {

            System.out.println("Inicia la generación de C3D...");
            LexerCup scan = new LexerCup(new BufferedReader(new StringReader(texto)));
            Sintax parser = new Sintax(scan);
            parser.parse();

            var_idSem = parser.getvar_idSemantico();
            var_identificador = parser.getvar_identificador();
            var_tipo_dato = parser.getvar_tipo_dato();
            var_valor = parser.getvar_valor();

            System.out.println(var_identificador.get(0).toString());

            pnlSalida.textPane.setText(pnlSalida.textPane.getText() + "----------Analisis finalizado----------");
            llenarVariable();
            
            GuardarDatosEnLista();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        //Comienza analisis semantico
        String erroresSem = "";
        for(int i = 0; i < var_idSem.size(); i++){
            int encontrado = -1;
            encontrado = LTINT.indexOf(var_idSem.get(i).toString());
            encontrado = (encontrado == -1) ? LENT.indexOf(var_idSem.get(i).toString()) : 1;
            encontrado = (encontrado == -1) ? LFLO.indexOf(var_idSem.get(i).toString()) : 1;
            encontrado = (encontrado == -1) ? LDOU.indexOf(var_idSem.get(i).toString()) : 1;
            encontrado = (encontrado == -1) ? LCHAR.indexOf(var_idSem.get(i).toString()) : 1;
            encontrado = (encontrado == -1) ? LBOL.indexOf(var_idSem.get(i).toString()) : 1;
            if (encontrado == -1) {
                erroresSem += "\nError semantico con identificador '" + var_idSem.get(i).toString() + "': no se ha declarado";
            }
        }
        pnlSalida.textPane.setText(pnlSalida.textPane.getText()+erroresSem);
        pnlSalida.textPane.setText(pnlSalida.textPane.getText() + "\n----------Analisis Semantico finalizado----------");
        var_identificador = new ArrayList();
        var_tipo_dato = new ArrayList();
        var_valor = new ArrayList();
        
    }
    
    void GuardarDatosEnLista(){
        //Crear linkedlist para meter todas las variables que tengamos en nuestra tabla de simbolos
        LTINT = new LinkedList<>();
        LENT = new LinkedList<>();
        LFLO = new LinkedList<>();
        LDOU = new LinkedList<>();
        LCHAR = new LinkedList<>();
        LBOL = new LinkedList<>();
        DefaultTableModel variables;
        variables = (DefaultTableModel) pts.tblVariables.getModel();
        for(int i = 0; i < variables.getRowCount(); i++){
            switch(variables.getValueAt(i, 1).toString()){
                case "int":
                    LENT.add(variables.getValueAt(i, 0).toString());
                    break;
                case "char":
                    LCHAR.add(variables.getValueAt(i, 0).toString());
                    break;
                case "float":
                    LFLO.add(variables.getValueAt(i, 0).toString());
                    break;
                case "boolean":
                    LBOL.add(variables.getValueAt(i, 0).toString());
                    break;
                case "double":
                    LDOU.add(variables.getValueAt(i, 0).toString());
                    break;
            }
        }
    }

    void llenarVariable() {         // llenar la tabla de variables
        DefaultTableModel variables;
        variables = (DefaultTableModel) pts.tblVariables.getModel();
        int varLenght = variables.getRowCount();
        for (int i = varLenght - 1; i >= 0; i--) {
            variables.removeRow(i);
        }

        int size = var_identificador.size();
        boolean entro = false;
        Object temp[] = new Object[3];
        System.err.println("Tamaño: " + size);
        for (int i = 0; i < size; i++) {
            temp[0] = var_identificador.get(i);
            temp[1] = var_tipo_dato.get(i);
            temp[2] = var_valor.get(i);
            if (variables.getRowCount() == 0) {
                variables.addRow(temp);
            } else {
                for (int j = 0; j < variables.getRowCount(); j++) {

                    if (variables.getValueAt(j, 0).toString().equalsIgnoreCase(temp[0].toString())) {
                        entro = true;
                    }

                }
                if (!entro) {

                    variables.addRow(temp);
                }
                entro = false;
            }

        }

        var_identificador = new ArrayList();
        var_tipo_dato = new ArrayList();
        var_valor = new ArrayList();
    }

    void llenarSimbolosDiamicos() {

        DefaultTableModel m;
        DefaultTableModel tok;

        tok = (DefaultTableModel) tblComps.getModel();
        m = (DefaultTableModel) ptd.tblDinSim.getModel();

        int rowCount = m.getRowCount();
        int rw = tok.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            m.removeRow(i);
        }

        for (int i = rw - 1; i >= 0; i--) {
            tok.removeRow(i);
        }

        Object lin[] = linea.toArray();
        Object toke[] = token.toArray();
        Object lex[] = lexema.toArray();

        rowCount = m.getRowCount();
        boolean inc = false;
        for (int i = 0; i < token.size(); i++) {
            //int inv=1;
            Object o[] = new Object[4];
            o[0] = m.getRowCount() + 1;
            o[1] = lex[i];
            o[2] = toke[i];
            o[3] = 1;
            if (o[2] != null) {
                System.out.println("jaja" + rowCount);
            }
            rowCount = m.getRowCount();
            inc = false;
            if (rowCount > 0) {
                if (o[2].toString().equalsIgnoreCase("Identificador")) {
                    for (int j = 0; j < rowCount; j++) {
                        if (m.getValueAt(j, 1).equals(o[1].toString())) {
                            m.setValueAt(Integer.parseInt(m.getValueAt(j, 3).toString()) + 1, j, 3);
                            inc = true;
                        }
                    }
                    if (!inc) {
                        m.addRow(o);

                    }

                }
            } else if ((o[2].toString().equalsIgnoreCase("Identificador")) && (inc == false)) {
                m.addRow(o);
            }

        }

        int j;
        Object temp[];
        int n = m.getRowCount();
        boolean swapped;
        for (int ki = 0; ki < n - 1; ki++) {
            swapped = false;
            for (j = 0; j < n - ki - 1; j++) {
                if (Integer.parseInt(m.getValueAt(j, 3).toString()) < Integer.parseInt(m.getValueAt(j + 1, 3).toString())) {
                    // swap arr[j] and arr[j+1]
                    temp = new Object[3];
                    temp[0] = m.getValueAt(j, 1);
                    temp[1] = m.getValueAt(j, 2);
                    temp[2] = m.getValueAt(j, 3);
                    //arr[j] = arr[j + 1];
                    m.setValueAt(m.getValueAt(j + 1, 1), j, 1);
                    m.setValueAt(m.getValueAt(j + 1, 2), j, 2);
                    m.setValueAt(m.getValueAt(j + 1, 3), j, 3);

                    //arr[j + 1] = temp;
                    m.setValueAt(temp[0], j + 1, 1);
                    m.setValueAt(temp[1], j + 1, 2);
                    m.setValueAt(temp[2], j + 1, 3);
                    swapped = true;
                }
            }

            // IF no two elements were
            // swapped by inner loop, then break
            if (swapped == false) {
                break;
            }
        }

        for (int i = 0; i < token.size(); i++) {

            Object o[] = new Object[3];

            o[0] = lin[i];
            o[1] = lex[i];
            o[2] = toke[i];

            tok.addRow(o);
        }
    }

    

    private void lineNumbers() {
        try {
            String str = codeArea.getText();

            // Plain Style
            SimpleAttributeSet plain = new SimpleAttributeSet();

            // Bold style
            SimpleAttributeSet bold = new SimpleAttributeSet();
            StyleConstants.setBold(bold, true);

            // Remove all from document
            Document doc = numeroLine.getDocument();
            doc.remove(0, doc.getLength());

            // Calculating the number of lines
            int length = str.length() - str.replaceAll("\n", "").length() + 1;
            numLineas = length;
            lblLines.setText(length + " lineas");
            // Adding line-numbers
            for (int i = 1; i <= length; i++) {

                // Non-bold line-numbers
                if (i < length) {
                    doc.insertString(doc.getLength(), i + "\n", plain);

                    // Last line-number bold
                } else {
                    doc.insertString(doc.getLength(), i + "\n", bold);
                }
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        Logo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Area = new javax.swing.JPanel();
        scrollCodeArea = new javax.swing.JScrollPane();
        codeArea = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblLines = new javax.swing.JLabel();
        jtabComp = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        scrollPaneComp = new javax.swing.JScrollPane();
        tblComps = new javax.swing.JTable();
        scrollNumeroLine = new javax.swing.JScrollPane();
        numeroLine = new javax.swing.JTextPane();
        lexico = new javax.swing.JLabel();
        udo = new javax.swing.JLabel();
        newFile = new javax.swing.JLabel();
        save = new javax.swing.JLabel();
        saveas = new javax.swing.JLabel();
        open = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        redo = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        exec = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        config = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JLabel();
        btnWeb = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 61));

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/logo.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 43)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(107, 232, 165));
        jLabel1.setText("Hiver");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/minimize.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.setPreferredSize(new java.awt.Dimension(48, 48));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/close.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.setPreferredSize(new java.awt.Dimension(48, 48));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        Area.setBackground(new java.awt.Color(78, 78, 92));
        Area.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollCodeArea.setBorder(null);
        scrollCodeArea.setPreferredSize(new java.awt.Dimension(1, 22));

        codeArea.setBackground(new java.awt.Color(78, 78, 92));
        codeArea.setBorder(null);
        codeArea.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        codeArea.setForeground(new java.awt.Color(255, 255, 255));
        codeArea.setCaretColor(new java.awt.Color(255, 255, 255));
        codeArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        codeArea.setPreferredSize(new java.awt.Dimension(100, 22));
        codeArea.setSelectedTextColor(new java.awt.Color(0, 204, 102));
        codeArea.setSelectionColor(new java.awt.Color(255, 255, 255));
        codeArea.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                codeAreaAncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        codeArea.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                codeAreaFocusGained(evt);
            }
        });
        codeArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                codeAreaMouseEntered(evt);
            }
        });
        scrollCodeArea.setViewportView(codeArea);

        Area.add(scrollCodeArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1400, 560));

        jPanel2.setBackground(new java.awt.Color(101, 101, 117));

        jLabel13.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Lineas:");

        lblLines.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 14)); // NOI18N
        lblLines.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(lblLines, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1630, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLines, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        Area.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 910, 1820, 40));

        jtabComp.setBackground(new java.awt.Color(51, 51, 61));
        jtabComp.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        Area.add(jtabComp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 1400, 330));

        jPanel3.setBackground(new java.awt.Color(101, 101, 117));

        scrollPaneComp.setBorder(null);

        tblComps.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblComps.setForeground(new java.awt.Color(255, 255, 255));
        tblComps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Línea", "Lexema", "Componente Léxico"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPaneComp.setViewportView(tblComps);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneComp, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPaneComp, javax.swing.GroupLayout.DEFAULT_SIZE, 899, Short.MAX_VALUE))
        );

        Area.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 0, 420, 910));

        scrollNumeroLine.setBorder(null);
        scrollNumeroLine.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollNumeroLine.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        numeroLine.setEditable(false);
        numeroLine.setBackground(new java.awt.Color(101, 101, 117));
        numeroLine.setBorder(null);
        numeroLine.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        numeroLine.setForeground(new java.awt.Color(153, 255, 204));
        numeroLine.setFocusable(false);
        scrollNumeroLine.setViewportView(numeroLine);

        Area.add(scrollNumeroLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 480));

        lexico.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/sintac.png"))); // NOI18N
        lexico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lexico.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                lexicoComponentAdded(evt);
            }
        });
        lexico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lexicoMouseClicked(evt);
            }
        });

        udo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/back.png"))); // NOI18N
        udo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        udo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                udoMouseClicked(evt);
            }
        });

        newFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/new.png"))); // NOI18N
        newFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        newFile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newFileMouseClicked(evt);
            }
        });

        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/guardar.png"))); // NOI18N
        save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveMouseClicked(evt);
            }
        });

        saveas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/saveas.png"))); // NOI18N
        saveas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        saveas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                saveasMouseClicked(evt);
            }
        });

        open.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/openFile.png"))); // NOI18N
        open.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        open.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openMouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Abrir");

        jLabel5.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Guardar");

        jLabel6.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nuevo");

        jLabel7.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Guardar como");

        jLabel8.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Deshacer");

        jLabel9.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Rehacer");

        redo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/redo.png"))); // NOI18N
        redo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        redo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                redoMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("An. Léxico");

        exec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/compile.png"))); // NOI18N
        exec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                execMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Compilar");

        config.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/config.png"))); // NOI18N
        config.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        config.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                configMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Buscar Palabra");

        jLabel14.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Ajustes");

        btnBuscar.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/search.png"))); // NOI18N
        btnBuscar.setText(" ");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });

        btnWeb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/globe.png"))); // NOI18N
        btnWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnWeb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWebMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Página Web");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Logo)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(newFile))
                            .addComponent(jLabel6))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(save))
                            .addComponent(jLabel5))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(open)
                            .addComponent(jLabel4))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(saveas))
                            .addComponent(jLabel7))
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(udo)
                            .addComponent(jLabel8))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(redo)
                            .addComponent(jLabel9))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lexico)
                            .addComponent(jLabel10))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(exec))
                            .addComponent(jLabel11))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(config)
                                .addGap(42, 42, 42)
                                .addComponent(btnWeb))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel16)))
                        .addGap(488, 488, 488)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnBuscar)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(newFile)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(save)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(open))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(saveas)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(udo)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(redo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(lexico)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel10))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(exec)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel11))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(config)
                                .addGap(2, 2, 2)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel16))))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnWeb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(Area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.setState(ICONIFIED);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            if (!name.getAbsolutePath().equals("")) {
                int confir = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios?", "Salir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confir == JOptionPane.YES_OPTION) {
                    guardar();
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        } catch (java.lang.NullPointerException ex) {
            System.exit(0);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void execMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_execMouseClicked
        p1.textPane.setText("");
        pnlSalida.textPane.setText("");
        //para saber sí se sube a git
        recuperartokens();
        analisisSintacttico();
        llenarSimbolosDiamicos();

        //agregamo las clases de los analizadores que se crean con jflex y cup

    }//GEN-LAST:event_execMouseClicked

    private void ini() {
        undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK);
        redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
        undoManager = new UndoManager();
        Document document = codeArea.getDocument();
        document.addUndoableEditListener((UndoableEditEvent e) -> {
            undoManager.addEdit(e.getEdit());
        });

        codeArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(undoKeyStroke, "undoKeyStroke");
        codeArea.getActionMap().put("undoKeyStroke", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                } catch (CannotUndoException cue) {
                }
            }
        });

        codeArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(redoKeyStroke, "redoKeyStroke");
        codeArea.getActionMap().put("redoKeyStroke", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {
                }
            }
        });
    }

    private int cuentaLin() {
        String A[] = codeArea.getText().split("\n");
        int p = 0;
        for (int i = 0; i < A.length; i++) {
            if (!A[i].equals("\n")) {
                p++;
            }
        }
        return p;
    }

    private void openMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openMouseClicked
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(this);//Terminar el editor de texto
            name = new File(chooser.getSelectedFile().toString());
            doc = codeArea.getStyledDocument();
            editorKit = new RTFEditorKit();
            java.io.FileInputStream fbr = new java.io.FileInputStream(name);
            try {
                codeArea.setText("");
                editorKit.read(fbr, doc, 0);
                lblLines.setText(cuentaLin() + " lineas");
            } catch (BadLocationException ex) {
                Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (java.lang.NullPointerException ex) {
        }
    }//GEN-LAST:event_openMouseClicked

    private void guardar() {
        try {
            if (!name.getName().equals("")) {
                fbw = new FileOutputStream(name, false);
                doc = codeArea.getStyledDocument();
                editorKit = new RTFEditorKit();
                try {
                    try {
                        editorKit.write(fbw, doc, 0, doc.getLength());
                    } catch (IOException ex) {
                        Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (NullPointerException err) {
            showMessageDialog(this, name.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveasMouseClicked
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(this);
            name = new File(chooser.getSelectedFile() + ".hiv");

            if (name.getName().matches("^[a-zA-Z0-9\\sñÑ]+[.][h][i][v]$")) {
                fbw = new FileOutputStream(name, false);
                doc = codeArea.getStyledDocument();
                editorKit = new RTFEditorKit();
                try {
                    editorKit.write(fbw, doc, 0, doc.getLength());
                } catch (BadLocationException ex) {
                    Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                if (name.getName().matches("^[a-zñA-ZÑ0-9\\s]+[.][h][i][v][.][h][i][v]$")) {
                    int confir = JOptionPane.showConfirmDialog(null, "¿Quieres remplazarlo?", "Archivo Existente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confir == JOptionPane.YES_OPTION) {
                        fbw = new FileOutputStream(name, false);
                        doc = codeArea.getStyledDocument();
                        editorKit = new RTFEditorKit();
                        try {
                            editorKit.write(fbw, doc, 0, doc.getLength());
                        } catch (BadLocationException ex) {
                            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    showMessageDialog(null, "Nombre o dirección mal ingresada");
                    saveas.requestFocus();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException err) {
        }
    }//GEN-LAST:event_saveasMouseClicked

    private void saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_saveMouseClicked
        guardar();
    }//GEN-LAST:event_saveMouseClicked

    private void redoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_redoMouseClicked
        try {
            undoManager.redo();
        } catch (CannotRedoException cre) {
        }
    }//GEN-LAST:event_redoMouseClicked

    private void udoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_udoMouseClicked
        try {
            undoManager.undo();
        } catch (CannotUndoException cue) {
        }
    }//GEN-LAST:event_udoMouseClicked

    private void configMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_configMouseClicked
        Setings sets = new Setings();
        sets.setAlwaysOnTop(true);
        sets.setVisible(true);
    }//GEN-LAST:event_configMouseClicked

    private void lexicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lexicoMouseClicked
        //recuperartokens();
        //llenarSimbolosDiamicos();
        //imprimirErrores();
    }//GEN-LAST:event_lexicoMouseClicked

    private void codeAreaAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_codeAreaAncestorMoved
        scrollNumeroLine.getVerticalScrollBar().setValue(scrollCodeArea.getVerticalScrollBar().getValue());
        System.err.println("se movio");
    }//GEN-LAST:event_codeAreaAncestorMoved

    private void codeAreaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeAreaMouseEntered
        System.out.println("Sí entro");
        if (entro) {
            scrollCodeArea.getVerticalScrollBar().setValue(10);
            scrollNumeroLine.getVerticalScrollBar().setValue(10);
            scrollCodeArea.getVerticalScrollBar().setValue(0);
            scrollNumeroLine.getVerticalScrollBar().setValue(0);
        }
        entro = false;

    }//GEN-LAST:event_codeAreaMouseEntered

    private void lexicoComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_lexicoComponentAdded
        System.out.println("Sí entro");
        if (entro) {
            scrollCodeArea.getVerticalScrollBar().setValue(10);
            scrollNumeroLine.getVerticalScrollBar().setValue(10);
            scrollCodeArea.getVerticalScrollBar().setValue(0);
            scrollNumeroLine.getVerticalScrollBar().setValue(0);
        }
        entro = false;
    }//GEN-LAST:event_lexicoComponentAdded

    private void newFileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newFileMouseClicked
        doc = (StyledDocument) codeArea.getDocument();
        this.dispose();
    }//GEN-LAST:event_newFileMouseClicked

    private void codeAreaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_codeAreaFocusGained
        Highlighter high = codeArea.getHighlighter();
        // Removemos cualquier subdrayado que se le haya hecho con anterioridad.
        high.removeAllHighlights();
    }//GEN-LAST:event_codeAreaFocusGained

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
       try {
            // Obtenemos la palabra que deseemos buscar.
            String word = JOptionPane.showInputDialog(null, "Ingresa la palabra que desee buscar:");
            // Obtener las cadenas del área de texto.
            String text = codeArea.getText();
            Highlighter high = codeArea.getHighlighter();
            // Removemos cualquier subdrayado que se le haya hecho con anterioridad.
            high.removeAllHighlights();
            // Invocamos el método para buscar todas los ocurrencias de la palabra buscada.
            List<Integer> listIDX = new ArrayList<Integer>();
            listIDX = findWord(text, word);
            for (int i = 0; i < listIDX.size(); i++) {
                System.out.println(listIDX.get(i));
                int p0 = listIDX.get(i);
                int p1 = listIDX.get(i) + word.trim().length();
                high.addHighlight(p0, p1, DefaultHighlighter.DefaultPainter);
            }
        } catch (BadLocationException e) {
            System.err.println("BadLocationException btnBuscarActionPerformed()\n" + e.getMessage());
        }
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void btnWebMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWebMouseClicked
        try {
            Runtime.getRuntime().exec("C:\\Windows\\System32\\cmd.exe /K start www.hiverlya2.wixsite.com/hiver");
        } catch (IOException ex) {
            Logger.getLogger(CompilerComp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnWebMouseClicked

    // Método para encontrar los índices de cada ocurrencia en el texto, al buscar una palabra
    public List<Integer> findWord(String textString, String word) {
        List<Integer> indexes = new ArrayList<Integer>();
        String lowerCaseTextString = textString.toLowerCase();
        String lowerCaseWord = word.toLowerCase();

        int index = 0;
        while (index != -1) {
            index = lowerCaseTextString.indexOf(lowerCaseWord, index);
            if (index != -1) {
                indexes.add(index);
                index++;
            }
        }
        return indexes;
    }

// Método para encontrar cadenas
    private int findLastWords(String word, int index) {
        while (--index >= 0) {
            if (String.valueOf(word.charAt(index)).matches("\\W")) {
                break;
            }
        }
        return index;
    }

    private int findFirstWords(String word, int index) {
        while (index < word.length()) {
            if (String.valueOf(word.charAt(index)).matches("\\W")) {
                break;
            }
            index++;
        }
        return index;
    }

    // Método para pintar las palabras reservadas
    private void colors() {
        final StyleContext content = StyleContext.getDefaultStyleContext();
        // Colores
        final AttributeSet attred = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 0));
        final AttributeSet attwhite = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(255, 255, 255));
        final AttributeSet attblue = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(0, 255, 255));
        final AttributeSet attyellow = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(255, 255, 0));
        final AttributeSet attorange = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(255, 128, 0));
        final AttributeSet attpink = content.addAttribute(content.getEmptySet(), StyleConstants.Foreground, new Color(255, 0, 255));
        // Estilo
        DefaultStyledDocument doc = new DefaultStyledDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);

                String text = getText(0, getLength());
                int before = findLastWords(text, offset);
                if (before < 0) {
                    before = 0;
                }
                int after = findFirstWords(text, offset + str.length());
                int wordL = before;
                int wordR = before;

                while (wordR <= after) {
                    if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\W")) {
                        if (text.substring(wordL, wordR).matches("(\\W)*(if|else|while|do|for|exit|return|function|void|null|input|output|in|out|def|true|false|start|end|array|type|display|config|scale|pin|volt|wait|mode|sleep|switch|default|name|max_volt)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attblue, false);
                        } else if (text.substring(wordL, wordR).matches("(\\W)*(TINYINT|INT|FLOAT|DOUBLE|CHAR|BOOLEAN|tinyint|int|float|double|char|boolean)")) {
                            setCharacterAttributes(wordL, wordR - wordL, attorange, false);
                        } else {
                            setCharacterAttributes(wordL, wordR - wordL, attwhite, false);
                        }
                        wordL = wordR;
                    }
                    wordR++;
                }
            }

            @Override
            public void remove(int offs, int len) {
                try {
                    super.remove(offs, len);
                    String text = getText(0, getLength());
                    int before = findLastWords(text, offs);
                    if (before < 0) {
                        before = 0;
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(CompilerComp.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }; //insertString

        JTextPane txt = new JTextPane(doc);
        String tempStr = codeArea.getText();
        codeArea.setStyledDocument(txt.getStyledDocument());
        codeArea.setText(tempStr);
    }

    private boolean entro;

    private void addTabs() {
        p1 = new pnlSalida();
        ptf = new pnlTablaFija();
        ptd = new pnlTablaDinamica();
        pts = new pnlTablaSimbolos();
        jtabComp.add("Salida", p1);
        jtabComp.add("Tablas fijas", ptf);
        jtabComp.add("Tablas dinámicas", ptd);
        jtabComp.add("Tabla de símbolos", pts);

    }

    public void codigo_lineas(String codigo, String lineas) {
        numeroLine.setText(lineas);
        codeArea.setText(codigo);

    }

    public void scroll_posicion(int posicion) {
        System.err.println("Actual pos: " + posicion);
        scrollCodeArea.getVerticalScrollBar().setValue(posicion);
        scrollNumeroLine.getVerticalScrollBar().setValue(posicion);
        if (posicion == 0) {
            entro = true;
        } else {
            entro = false;
        }
        System.err.println("Actual: " + scrollCodeArea.getVerticalScrollBar().getValue());
        //codeArea.select(posicion, posicion);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompilerComp.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompilerComp.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompilerComp.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompilerComp.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompilerComp().setVisible(true);
            }
        });
    }
    // ANALIZADOR SEMANTICO
    public LinkedList <String> LTINT;
    public LinkedList <String> LENT;
    public LinkedList <String> LFLO;
    public LinkedList <String> LDOU;
    public LinkedList <String> LCHAR;
    public LinkedList <String> LBOL;
    public LinkedList <String> LPIN;
    //
    public ArrayList var_idSem;
    public ArrayList linea;
    public ArrayList token;
    public ArrayList lexema;
    public ArrayList error;
    // para la tabla de variables
    public ArrayList var_identificador;
    public ArrayList var_tipo_dato;
    public ArrayList var_valor;
    // para los errores
    public ArrayList err_linea;
    public ArrayList err_lexema;
    private String fuentes[];
    private FileOutputStream fbw;
    private Font fuent;
    private int size = 12;
    public int numLineas = 1;
    private File name;
    private int start;
    private int end;
    private StyledDocument doc;
    private Element element;
    private AttributeSet as;
    private MutableAttributeSet asNew;
    private SimpleAttributeSet align;
    private KeyStroke undoKeyStroke;
    private KeyStroke redoKeyStroke;
    private UndoManager undoManager;
    private RTFEditorKit editorKit;
    private pnlSalida p1;
    private pnlTablaFija ptf;
    private pnlTablaDinamica ptd;
    private pnlTablaSimbolos pts;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Area;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel btnBuscar;
    private javax.swing.JLabel btnWeb;
    public static javax.swing.JTextPane codeArea;
    private javax.swing.JLabel config;
    private javax.swing.JLabel exec;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jtabComp;
    private javax.swing.JLabel lblLines;
    private javax.swing.JLabel lexico;
    private javax.swing.JLabel newFile;
    private javax.swing.JTextPane numeroLine;
    private javax.swing.JLabel open;
    private javax.swing.JLabel redo;
    private javax.swing.JLabel save;
    private javax.swing.JLabel saveas;
    public static javax.swing.JScrollPane scrollCodeArea;
    public static javax.swing.JScrollPane scrollNumeroLine;
    private javax.swing.JScrollPane scrollPaneComp;
    private javax.swing.JTable tblComps;
    private javax.swing.JLabel udo;
    // End of variables declaration//GEN-END:variables
}
