package UI;

import Client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;

public class PrivateChat extends Thread {
    private Client client;
    private String destination;
    private JFrame frame;

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    private InetAddress inetReceive;

    public PrivateChat(Client client, String userIP){

        this.client = client;
        destination = userIP.substring(0, userIP.indexOf(':'));

        frame = new JFrame("Private Conversation: " + destination);

        try {
            socket = new DatagramSocket(8080);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        initComponents();

        frame.setVisible(true);
        frame.pack();
        start();
    }

    private void initComponents(){
        mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.9;
        c.gridwidth = 2;

        c.gridx = 0;
        c.gridy = 0;
        conversationTextArea = new JTextArea();
        mainPanel.add(new JScrollPane(conversationTextArea), c);

        c.weighty = 0.1;
        c.gridy = 1;
        c.weightx = 0.9;

        c.gridwidth =1;
        messageToSendTextArea = new JTextArea();
        mainPanel.add(new JScrollPane(messageToSendTextArea), c);

        c.gridx = 1;
        c.weightx = 0.1;
        sendButton = new JButton("Send");
        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(destination, messageToSendTextArea.getText());
                messageToSendTextArea.setText("");
            }
        });
        mainPanel.add(sendButton, c);
        frame.setContentPane(mainPanel);
    }

    public void sendMessage(String ip, String message){
        try {
            DatagramPacket dp = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(ip), 8080);
            socket.send(dp);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            byte[] buffer = new byte[254];
            DatagramPacket dp = new DatagramPacket(buffer, 254);
            try {
                socket.receive(dp);
                inetReceive = dp.getAddress();
                byte data[] = dp.getData();
                int len = dp.getLength();
                String result = new String(data);
                conversationTextArea.setText(conversationTextArea.getText()+"\n===\n" + result);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private JPanel mainPanel;
    private JTextArea conversationTextArea;
    private JTextArea messageToSendTextArea;
    private JButton sendButton;


}
