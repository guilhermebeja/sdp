/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Client.Client;
import Server.StatusCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author francisco
 */
public class ClientUI extends javax.swing.JFrame {
    private DefaultListModel friendListModel;

    private Client client;

    /**
     * Creates new form ClientUI
     */
    public ClientUI() {
        initComponents();
        init();

        refreshFriendsList();
    }

    private void init(){
        client = new Client("Ciscomarte");
        friendListModel = new DefaultListModel();
        friendList.setModel(friendListModel);
    }

    public void refreshFriendsList(){

        friendListModel.setSize(0);
        ArrayList<String> allFriends = null;
        try {
            allFriends = client.getAllFriends();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(String friend : allFriends){
            friendListModel.addElement(friend);
        }
    }

    public void addFriend(){

    }

    public void removeFriend(){

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        friendListRightClick = new javax.swing.JPopupMenu();
        removeFriendButton = new javax.swing.JMenuItem();
        friendsPanel = new javax.swing.JPanel();
        addFriendButton = new javax.swing.JButton();
        friendText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendList = new javax.swing.JList<>();

        removeFriendButton.setText("Remove");
        removeFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFriendButtonActionPerformed(evt);
            }
        });
        friendListRightClick.add(removeFriendButton);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        friendsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        addFriendButton.setText("Add");
        addFriendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriendButtonActionPerformed(evt);
            }
        });

        friendText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendTextMouseClicked(evt);
            }
        });
        friendText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                friendTextActionPerformed(evt);
            }
        });

        friendList.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        friendList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        friendList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                friendListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(friendList);

        javax.swing.GroupLayout friendsPanelLayout = new javax.swing.GroupLayout(friendsPanel);
        friendsPanel.setLayout(friendsPanelLayout);
        friendsPanelLayout.setHorizontalGroup(
            friendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(friendsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(friendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(friendsPanelLayout.createSequentialGroup()
                        .addComponent(friendText, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addFriendButton)))
                .addContainerGap())
        );
        friendsPanelLayout.setVerticalGroup(
            friendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(friendsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(friendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addFriendButton)
                    .addComponent(friendText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(friendsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(576, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(friendsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriendButtonActionPerformed
        StatusCode status = client.addFriend(friendText.getText());
        if(status.equals(StatusCode.NOT_FOUND)){
            friendText.setBackground(new Color(236, 0, 6));

        }
        else{
            friendText.setText("");
        }

        refreshFriendsList();
    }//GEN-LAST:event_addFriendButtonActionPerformed

    private void removeFriendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFriendButtonActionPerformed
        client.removeFriend((String)friendListModel.getElementAt(friendList.getSelectedIndex()));
        refreshFriendsList();
    }//GEN-LAST:event_removeFriendButtonActionPerformed

    private void friendListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendListMouseClicked
        if(evt.getButton() == MouseEvent.BUTTON3 && friendList.getSelectedIndex() != -1){
            friendListRightClick.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_friendListMouseClicked

    private void friendTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_friendTextActionPerformed
        
    }//GEN-LAST:event_friendTextActionPerformed

    private void friendTextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_friendTextMouseClicked
        friendText.setBackground(Color.WHITE);
    }//GEN-LAST:event_friendTextMouseClicked

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
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFriendButton;
    private javax.swing.JList<String> friendList;
    private javax.swing.JPopupMenu friendListRightClick;
    private javax.swing.JTextField friendText;
    private javax.swing.JPanel friendsPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem removeFriendButton;
    // End of variables declaration//GEN-END:variables
}
