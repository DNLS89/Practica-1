/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GestorTarjetasFE;

import SQL.SQL;
import Tramite.CancelacionBE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author debian
 */
public class Cancelacion extends javax.swing.JPanel {
    private SQL sql;
    /**
     * Creates new form solicitud
     */
    public Cancelacion(SQL sql) {
        this.sql = sql;
        initComponents();
    }
    
    public JLabel getTxtDireccion() {
        return txtDireccion;
    }

    public JLabel getTxtNombreUsuario() {
        return txtNombreUsuario;
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
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroTarjeta = new javax.swing.JTextField();
        btnAutorizar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(980, 440));

        jPanel1.setPreferredSize(new java.awt.Dimension(980, 440));

        jLabel5.setText("CANCELAR TARJETA");

        jLabel1.setText("Para cancelar una tarjeta ingrese los siguientes datos:");

        jLabel4.setText("Número de Tarjeta:");

        txtNumeroTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroTarjetaActionPerformed(evt);
            }
        });
        txtNumeroTarjeta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroTarjetaKeyTyped(evt);
            }
        });

        btnAutorizar.setText("Cancelar Tarjeta");
        btnAutorizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizarActionPerformed(evt);
            }
        });

        jLabel2.setText("Datos Dueño Tarjeta:");

        txtNombreUsuario.setText("Nombre:");

        txtDireccion.setText("Dirección: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(474, 474, 474)
                .addComponent(btnAutorizar)
                .addGap(0, 368, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(273, 273, 273)
                .addComponent(jLabel4)
                .addGap(98, 98, 98)
                .addComponent(txtNumeroTarjeta)
                .addGap(179, 179, 179))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(445, 445, 445)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(181, 181, 181)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(78, 78, 78)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNumeroTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDireccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(btnAutorizar)
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumeroTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroTarjetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroTarjetaActionPerformed

    public boolean camposLlenadosCorrectamente() {
        if (txtNumeroTarjeta.getText().isBlank()) {
            JOptionPane.showMessageDialog(null, "Llena el apartado para continuar", "ERROR", JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void btnAutorizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorizarActionPerformed
        // TODO add your handling code here:
        if (camposLlenadosCorrectamente()) {
            CancelacionBE cancelacionBE = new CancelacionBE(txtNumeroTarjeta.getText(), sql, this);
            cancelacionBE.mostrarDatosInterfaz();
            
        }
    }//GEN-LAST:event_btnAutorizarActionPerformed

    private void txtNumeroTarjetaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroTarjetaKeyTyped
        // TODO add your handling code here:
        char c = evt.getKeyChar();
        
        if (!Character.isDigit(c) && !Character.isWhitespace(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroTarjetaKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutorizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel txtDireccion;
    private javax.swing.JLabel txtNombreUsuario;
    private javax.swing.JTextField txtNumeroTarjeta;
    // End of variables declaration//GEN-END:variables
}
