/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Client.Client;
import DataStructures.Conversation;
import DataStructures.Message;
import Server.StatusCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author francisco
 */
public class ClientUI extends javax.swing.JFrame {
    private DefaultListModel friendListModel;

    private Client client;

    private Conversation currentConversation;

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
        ArrayList<String> allFriends = client.getAllFriends();

        for(String friend : allFriends){
            friendListModel.addElement(friend);
        }
    }

    public void addFriend(){

    }

    public void removeFriend(){

    }

    public void loadConversation(Conversation c){
        currentConversation = c;
        // clean message panel
        conversationArea.setText("");

        // Sort messages by time
        c.getMessages().sort(new Comparator<Message>() {
            @Override
            public int compare(Message m1, Message m2) {
                if(m1.getTime() < m2.getTime()){
                    return -1;
                }
                else if(m1.getTime() > m2.getTime()){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });

        for(Message m : c.getMessages()){
            addMessage(m.getContent());
        }
    }

    public void addMessage(String content){
        conversationArea.setText(conversationArea.getText()+content+"\n---------------");
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
        chatPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        conversationArea = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        sendMsgText = new javax.swing.JTextArea();
        sendMsgButton = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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
        friendList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendListValueChanged(evt);
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );

        chatPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane2.setViewportView(conversationArea);

        sendMsgText.setColumns(20);
        sendMsgText.setRows(5);
        jScrollPane3.setViewportView(sendMsgText);

        sendMsgButton.setText("Send");
        sendMsgButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMsgButtonActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(chatPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendMsgButton, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(chatPanelLayout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendMsgButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(friendsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chatPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(friendsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void friendListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendListValueChanged
        Conversation conv = client.startConversation(Utilities.generateConversationID(client.getUsername()+friendListModel.getElementAt(evt.getFirstIndex())));
        loadConversation(conv);
    }//GEN-LAST:event_friendListValueChanged

    private void sendMsgButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMsgButtonActionPerformed
        client.sendMessage(currentConversation.getId(), sendMsgText.getText());
        addMessage(sendMsgText.getText());
    }//GEN-LAST:event_sendMsgButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Conversation conv = client.startConversation(currentConversation.getId());
        loadConversation(conv);
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JPanel chatPanel;
    private javax.swing.JTextPane conversationArea;
    private javax.swing.JList<String> friendList;
    private javax.swing.JPopupMenu friendListRightClick;
    private javax.swing.JTextField friendText;
    private javax.swing.JPanel friendsPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JMenuItem removeFriendButton;
    private javax.swing.JButton sendMsgButton;
    private javax.swing.JTextArea sendMsgText;
    // End of variables declaration//GEN-END:variables
}
