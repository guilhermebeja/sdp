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
                //192.168.1.76
                client.sendMessage(
                        textField1.getText().split(":")[0],
                        Integer.parseInt(textField1.getText().split(":")[1]),
                        textArea1.getText());
            }
        });

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (InstantiationException e) {
            System.out.println(e.getMessage());
        } catch (UnsupportedLookAndFeelException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ClientInterface cl = new ClientInterface();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
