package UserInterface;

import Client.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientInterface extends JFrame{
    Client client = new Client("Gui", "asd", "127.0.0.1", 8080);

    private JPanel mainPanel;
    private JTextField textField1;
    private JButton sendButton;
    private JTextArea textArea1;

    public ClientInterface() {
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.sendMessage("192.168.1.96", 8080, textArea1.getText());
            }
        });
    }

    public static void main(String[] args) {
        ClientInterface cl = new ClientInterface();
    }
}
