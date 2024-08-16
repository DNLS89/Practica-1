/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GestorArchivo;

import GestorTarjetasFE.Autorizacion;
import GestorTarjetasFE.Cancelacion;
import GestorTarjetasFE.Consultar;
import GestorTarjetasFE.Movimiento;
import GestorTarjetasFE.Solicitud;
import SQL.SQL;
import java.awt.BorderLayout;
import java.io.Console;
import javax.swing.JPanel;

/**
 *
 * @author debian
 */
public class GestorTarjeta extends javax.swing.JPanel {

    private SQL sql;
    /**
     * Creates new form PruebaELIMINAR
     */
    public GestorTarjeta(SQL sql) {
        this.sql = sql;
        initComponents();
    }
    
    public void showPanel(JPanel p) {
        p.setSize(980, 440);
        p.setLocation(0, 0);
        
        contenidoGestorTarjeta.removeAll();
        contenidoGestorTarjeta.add(p, BorderLayout.CENTER);
        contenidoGestorTarjeta.revalidate();
        contenidoGestorTarjeta.repaint();
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
        btnSolicitarTarjeta = new javax.swing.JButton();
        btnRegistrarMovimiento = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnAutorizarTarjeta = new javax.swing.JButton();
        btnCancelarTarjeta = new javax.swing.JButton();
        contenidoGestorTarjeta = new javax.swing.JPanel();

        btnSolicitarTarjeta.setText("Solicitar Tarjeta");
        btnSolicitarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSolicitarTarjetaActionPerformed(evt);
            }
        });

        btnRegistrarMovimiento.setText("Registrar Movimiento");
        btnRegistrarMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarMovimientoActionPerformed(evt);
            }
        });

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnAutorizarTarjeta.setText("Autorizar Tarjeta");
        btnAutorizarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutorizarTarjetaActionPerformed(evt);
            }
        });

        btnCancelarTarjeta.setText("Cancelar Tarjeta");
        btnCancelarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout contenidoGestorTarjetaLayout = new javax.swing.GroupLayout(contenidoGestorTarjeta);
        contenidoGestorTarjeta.setLayout(contenidoGestorTarjetaLayout);
        contenidoGestorTarjetaLayout.setHorizontalGroup(
            contenidoGestorTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        contenidoGestorTarjetaLayout.setVerticalGroup(
            contenidoGestorTarjetaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(btnSolicitarTarjeta)
                .addGap(62, 62, 62)
                .addComponent(btnRegistrarMovimiento)
                .addGap(58, 58, 58)
                .addComponent(btnConsultar)
                .addGap(54, 54, 54)
                .addComponent(btnAutorizarTarjeta)
                .addGap(57, 57, 57)
                .addComponent(btnCancelarTarjeta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(contenidoGestorTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSolicitarTarjeta)
                    .addComponent(btnRegistrarMovimiento)
                    .addComponent(btnConsultar)
                    .addComponent(btnAutorizarTarjeta)
                    .addComponent(btnCancelarTarjeta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(contenidoGestorTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarTarjetaActionPerformed
        // TODO add your handling code here:
        Cancelacion cancelacion = new Cancelacion(sql);
        showPanel(cancelacion);
    }//GEN-LAST:event_btnCancelarTarjetaActionPerformed

    private void btnSolicitarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSolicitarTarjetaActionPerformed
        // TODO add your handling code here:
        Solicitud solicitud = new Solicitud(sql);
        showPanel(solicitud);
    }//GEN-LAST:event_btnSolicitarTarjetaActionPerformed

    private void btnRegistrarMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarMovimientoActionPerformed
        // TODO add your handling code here:
        Movimiento movimiento = new Movimiento(sql);
        showPanel(movimiento);
    }//GEN-LAST:event_btnRegistrarMovimientoActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        // TODO add your handling code here:
        Consultar consultar = new Consultar(sql);
        showPanel(consultar);
    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnAutorizarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutorizarTarjetaActionPerformed
        // TODO add your handling code here:
        Autorizacion autorizacion = new Autorizacion(sql);
        showPanel(autorizacion);
    }//GEN-LAST:event_btnAutorizarTarjetaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAutorizarTarjeta;
    private javax.swing.JButton btnCancelarTarjeta;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnRegistrarMovimiento;
    private javax.swing.JButton btnSolicitarTarjeta;
    private javax.swing.JPanel contenidoGestorTarjeta;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
