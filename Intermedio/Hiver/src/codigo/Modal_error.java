package codigo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juana
 */
public class Modal_error extends javax.swing.JFrame {

    /**
     * Creates new form Modal_error
     */
    int x,y;
    public Modal_error() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCerrar = new javax.swing.JLabel();
        pnlModal = new javax.swing.JPanel();
        scrollGram = new javax.swing.JScrollPane();
        grammarsArea = new javax.swing.JEditorPane();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Graphics/close.png"))); // NOI18N
        lblCerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCerrar.setPreferredSize(new java.awt.Dimension(48, 48));
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
        });
        getContentPane().add(lblCerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, -1, -1));

        pnlModal.setBackground(new java.awt.Color(51, 51, 61));
        pnlModal.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlModalMouseDragged(evt);
            }
        });
        pnlModal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlModalMousePressed(evt);
            }
        });
        pnlModal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        grammarsArea.setContentType("text/html"); // NOI18N
        grammarsArea.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        scrollGram.setViewportView(grammarsArea);

        pnlModal.add(scrollGram, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 760, 300));

        lblError.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblError.setForeground(new java.awt.Color(255, 255, 255));
        pnlModal.add(lblError, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 740, 60));

        getContentPane().add(pnlModal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 780, 480));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
      this.dispose();
    }//GEN-LAST:event_lblCerrarMouseClicked
    
    public void addData(String error, String gram){
        lblError.setText(error);
        grammarsArea.setText(gram);
    }
    
    private void pnlModalMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModalMousePressed
        x= evt.getX();
        y= evt.getY();
    }//GEN-LAST:event_pnlModalMousePressed

    private void pnlModalMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlModalMouseDragged
        this.setLocation(this.getLocation().x+evt.getX()-x, this.getLocation().y+evt.getY()-y);
    }//GEN-LAST:event_pnlModalMouseDragged

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
            java.util.logging.Logger.getLogger(Modal_error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modal_error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modal_error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modal_error.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Modal_error().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JEditorPane grammarsArea;
    public static javax.swing.JLabel lblCerrar;
    public static javax.swing.JLabel lblError;
    public static javax.swing.JPanel pnlModal;
    public static javax.swing.JScrollPane scrollGram;
    // End of variables declaration//GEN-END:variables
}
