/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.sd.boulderdash.server;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tiagocardoso
 */
public class BoulderDashServerGUI extends javax.swing.JFrame {

    private BoulderDashServerImpl bds;

    /**
     * Creates new form BouderDashServerGUI
     */
    public BoulderDashServerGUI(BoulderDashServerImpl bds) {
        this.bds = bds;
        initComponents();

        this.setVisible(true);
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
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanelGeral = new javax.swing.JPanel();
        jpInformation = new javax.swing.JPanel();
        lblConnectedClients = new javax.swing.JLabel();
        lblConnectedClientsValue = new javax.swing.JLabel();
        lblLobbys = new javax.swing.JLabel();
        lblLobbysValue = new javax.swing.JLabel();
        jPanelLobbys = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListLobbys = new javax.swing.JList<>();
        jPanelPlayers = new javax.swing.JPanel();
        listPlayers = new java.awt.List();
        btnShutdown = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpInformation.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));
        jpInformation.setToolTipText("Information");

        lblConnectedClients.setText("Connected Clients:");

        lblConnectedClientsValue.setText("0");

        lblLobbys.setText("Lobbys:");

        lblLobbysValue.setText("0");

        javax.swing.GroupLayout jpInformationLayout = new javax.swing.GroupLayout(jpInformation);
        jpInformation.setLayout(jpInformationLayout);
        jpInformationLayout.setHorizontalGroup(
            jpInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpInformationLayout.createSequentialGroup()
                        .addComponent(lblConnectedClients)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblConnectedClientsValue))
                    .addGroup(jpInformationLayout.createSequentialGroup()
                        .addComponent(lblLobbys)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLobbysValue)))
                .addContainerGap(235, Short.MAX_VALUE))
        );
        jpInformationLayout.setVerticalGroup(
            jpInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpInformationLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jpInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConnectedClients)
                    .addComponent(lblConnectedClientsValue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLobbys)
                    .addComponent(lblLobbysValue))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelGeralLayout = new javax.swing.GroupLayout(jPanelGeral);
        jPanelGeral.setLayout(jPanelGeralLayout);
        jPanelGeralLayout.setHorizontalGroup(
            jPanelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelGeralLayout.setVerticalGroup(
            jPanelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelGeralLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jpInformation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
        );

        jTabbedPane.addTab("Geral", jPanelGeral);

        jScrollPane1.setViewportView(jListLobbys);

        javax.swing.GroupLayout jPanelLobbysLayout = new javax.swing.GroupLayout(jPanelLobbys);
        jPanelLobbys.setLayout(jPanelLobbysLayout);
        jPanelLobbysLayout.setHorizontalGroup(
            jPanelLobbysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLobbysLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelLobbysLayout.setVerticalGroup(
            jPanelLobbysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLobbysLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("Lobbys", jPanelLobbys);

        javax.swing.GroupLayout jPanelPlayersLayout = new javax.swing.GroupLayout(jPanelPlayers);
        jPanelPlayers.setLayout(jPanelPlayersLayout);
        jPanelPlayersLayout.setHorizontalGroup(
            jPanelPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
        );
        jPanelPlayersLayout.setVerticalGroup(
            jPanelPlayersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listPlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );

        jTabbedPane.addTab("Players", jPanelPlayers);

        btnShutdown.setText("Shutdown Server");
        btnShutdown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShutdownActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShutdown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShutdown)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShutdownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShutdownActionPerformed
        bds.shutdown();
    }//GEN-LAST:event_btnShutdownActionPerformed

    protected void addConnectedClient(String username, int value) {
        this.listPlayers.add(username);
        this.lblConnectedClientsValue.setText(String.valueOf(value));
        try {
            this.bds.setState(new State().new ConnectedClients(value));
        } catch (RemoteException ex) {
            Logger.getLogger(BoulderDashServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void removeConnectedClient(String username, int value) {
        this.listPlayers.remove(username);
        this.lblConnectedClientsValue.setText(String.valueOf(value));
        try {
            this.bds.setState(new State().new ConnectedClients(value));
        } catch (RemoteException ex) {
            Logger.getLogger(BoulderDashServerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnShutdown;
    private javax.swing.JList<String> jListLobbys;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelGeral;
    private javax.swing.JPanel jPanelLobbys;
    private javax.swing.JPanel jPanelPlayers;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JPanel jpInformation;
    private javax.swing.JLabel lblConnectedClients;
    private javax.swing.JLabel lblConnectedClientsValue;
    private javax.swing.JLabel lblLobbys;
    private javax.swing.JLabel lblLobbysValue;
    private java.awt.List listPlayers;
    // End of variables declaration//GEN-END:variables
}
