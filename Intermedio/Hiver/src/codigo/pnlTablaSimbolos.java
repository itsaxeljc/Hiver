package codigo;


import java.awt.Color;
import java.awt.Dimension;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juana
 */
public class pnlTablaSimbolos extends javax.swing.JPanel {

    /**
     * Creates new form pnlTablaFija
     */
    public pnlTablaSimbolos() {
        initComponents();
        tblVariables.setShowGrid(false);
        tblVariables.setIntercellSpacing(new Dimension(0,0));
        tblVariables.setOpaque(false);
        ((DefaultTableCellRenderer)tblVariables.getDefaultRenderer(Object.class)).setOpaque(false);
        scrollPalRes.setOpaque(false);
        scrollPalRes.getViewport().setOpaque(false);
        JTableHeader header = tblVariables.getTableHeader();
        header.setOpaque(false);
        header.setBackground(new Color(51,51,61));
        header.setForeground(Color.WHITE);
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPalRes = new javax.swing.JScrollPane();
        tblVariables = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(101, 101, 117));
        setMinimumSize(new java.awt.Dimension(1390, 325));
        setPreferredSize(new java.awt.Dimension(1390, 325));

        tblVariables.setBackground(new java.awt.Color(101, 101, 117));
        tblVariables.setFont(new java.awt.Font("Microsoft JhengHei UI", 0, 18)); // NOI18N
        tblVariables.setForeground(new java.awt.Color(255, 255, 255));
        tblVariables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Identificador", "Rol", "Ambito", "Tipo", "Linea Inicio", "Linea Fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPalRes.setViewportView(tblVariables);

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tabla de símbolos de variables");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPalRes, javax.swing.GroupLayout.PREFERRED_SIZE, 1301, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(scrollPalRes, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane scrollPalRes;
    public javax.swing.JTable tblVariables;
    // End of variables declaration//GEN-END:variables
}