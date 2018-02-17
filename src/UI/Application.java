package UI;

import Client.*;
import DataStructures.Conversation;
import DataStructures.Message;
import Exceptions.ClientException;
import Interfaces.Observer;
import Server.StatusCode;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Application extends JFrame implements Observer {
    private Client client;
    private DefaultListModel friendListModel;
    private Conversation currentConversation = null;
    public Application(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        pack();

        try {
            client = new Client(); // start the client and with it the connection to the server
            client.observers.add(this);
        } catch (ClientException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(0);
        }

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    //region Init Components
    private void init(){
        initFonts();
        initRegisterPanel();
        initLoginPanel();
        initChatPanel();
        initMainPanel();

    }

    private void initFonts(){
        bigFont = new Font("Verdana", Font.PLAIN, 18);
    }

    private void initRegisterPanel(){
        registerPanel = new JPanel(new GridBagLayout()); // set GridBad layout
        GridBagConstraints c = new GridBagConstraints(); // Layout manager constraints (positioners, etc)

        //instanciate components
        registerUsernameText = new JTextField("Username");
        registerPasswordField = new JPasswordField("password");
        registerPasswordFieldCheck = new JPasswordField("password");
        registerEmailField = new JTextField("Email");
        registerConfirmButton = new JButton("Confirm");

        //add components to the panel
        c.fill = GridBagConstraints.VERTICAL;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.ipady = 20;
        c.ipadx = 150;
        c.gridwidth = 2;

        c.gridx = 0;
        c.gridy = 1;
        registerPanel.add(registerUsernameText, c);

        c.gridx = 0;
        c.gridy = 2;
        registerPanel.add(registerPasswordField, c);

        c.gridx = 0;
        c.gridy = 3;
        registerPanel.add(registerPasswordFieldCheck, c);

        c.gridx = 0;
        c.gridy = 4;
        registerPanel.add(registerEmailField, c);

        c.gridx = 0;
        c.gridy = 5;
        registerPanel.add(registerConfirmButton ,c);

        //Component actions
        registerConfirmButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
    }

    private void initLoginPanel(){
        loginPanel = new JPanel(new GridBagLayout()); // set GridBag layout
        GridBagConstraints c = new GridBagConstraints(); // Layout manager constraints (positioners, etc)

        //instanciate components
        loginUsernameTextField = new JTextField("Username");
        loginUsernameTextField.setFont(bigFont);

        loginPasswordTextField = new JPasswordField("password");
        loginUsernameTextField.setFont(bigFont);

        loginButton = new JButton("Login");
        loginButton.setFont(bigFont);

        registerButton = new JButton("Register");
        registerButton.setFont(bigFont);

        //add components to the panel
        c.fill = GridBagConstraints.VERTICAL;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,0,0,0);
        c.ipady = 20;
        c.ipadx = 150;
        c.gridwidth = 2;

        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(loginUsernameTextField, c);

        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(loginPasswordTextField, c);

        c.gridwidth = 1;
        c.ipadx = 75;
        c.insets = new Insets(10,0, 0, 10);
        c.gridx = 0;
        c.gridy = 2;
        loginPanel.add(loginButton, c);

        c.insets = new Insets(10,0, 0, 0);
        c.gridx = 1;
        c.gridy = 2;
        loginPanel.add(registerButton, c);

        //Component actions

        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCard("registerPanelCard");
            }
        });
    }

    private void initChatPanel(){
        chatPanel = new JPanel(new GridBagLayout()); // set null layout
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Files");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        fileMenu.add(logoutMenuItem);

        menuBar.add(fileMenu);

        c.gridx = 0;
        c.gridy = 0;
        chatPanel.add(menuBar, c);

        //instanciate components
        friendsPanel = new JPanel(new GridBagLayout());
        friendsPanel.setBorder(new LineBorder(Color.BLACK, 1));

        chatWindow = new JPanel(new GridBagLayout());
        chatWindow.setBorder(new LineBorder(Color.BLACK, 1));


        c.weighty = 1.0;
        c.weightx = 0.1;

        c.gridx = 0;
        c.gridy = 1;
        chatPanel.add(friendsPanel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.9;
        c.weighty = 0;
        chatPanel.add(chatWindow, c);

        initFriendsPanel();
        initChatWindow();
    }

    private void initFriendsPanel(){
        friendListModel = new DefaultListModel();
        friendsList = new JList(friendListModel);
        friendSearchText = new JTextField("Search People");

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.8;
        c.gridx = 0;
        c.gridy = 0;
        friendsPanel.add(friendSearchText, c);

        c.weightx = 0.2;
        addFriendButton = new JButton("Add");
        c.gridx = 1;
        c.gridy = 0;
        friendsPanel.add(addFriendButton, c);

        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth=2;
        JScrollPane sp = new JScrollPane(friendsList);
        friendsPanel.add(sp, c);

        acceptFriendRequest = new JMenuItem("Accept");
        rejectFriendRequest = new JMenuItem("Reject");
        removeFriend = new JMenuItem("Remove");
        startPrivateConversation = new JMenuItem("Start Private Chat");
        startConversation = new JMenuItem("Chat");

        acceptFriendRequest.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Client.Friend f = (Client.Friend)friendListModel.get(friendsList.getSelectedIndex());
            client.acceptFriendRequest(f.username);
            }
        });

        rejectFriendRequest.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Client.Friend f = (Client.Friend)friendListModel.get(friendsList.getSelectedIndex());
            client.removeFriend(f.username);
            }
        });

        removeFriend.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.Friend f = (Client.Friend)friendListModel.get(friendsList.getSelectedIndex());
                client.removeFriend(f.username);
            }
        });

        startPrivateConversation.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.getUserIP(((Client.Friend)friendListModel.get(friendsList.getSelectedIndex())).username);
            }
        });

        startConversation.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchCurrentConversation();
            }
        });

        addFriendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.createFriendRequest(friendSearchText.getText().trim());
            }
        });

        friendsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int index = friendsList.locationToIndex(e.getPoint());
            if(index<0){
                friendsList.setSelectedIndex(-1);
            }
            else{
                if(e.getButton()==MouseEvent.BUTTON3){
                    friendsList.setSelectedIndex(index);
                    Client.Friend f = (Client.Friend)friendListModel.get(index);
                    JPopupMenu friendPopup = new JPopupMenu();
                    if(f.isFriendRequestReceived){
                        friendPopup.add(acceptFriendRequest);
                        friendPopup.add(rejectFriendRequest);
                    }

                    else if(f.friendRequestSent){
                        friendPopup.add(removeFriend);
                    }

                    else{
                        friendPopup.add(startConversation);
                        friendPopup.add(startPrivateConversation);
                        friendPopup.add(removeFriend);
                    }

                    friendPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
            }
        });

        friendsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
    }

    private void initChatWindow(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 0.8;
        c.weightx = 1;
        c.gridwidth = 2;

        conversationTextArea = new JTextArea("");
        conversationTextArea.setEditable(false);
        c.gridx = 0;
        c.gridy = 0;
        chatWindow.add(new JScrollPane(conversationTextArea), c);

        messageToSendTextArea = new JTextArea("");
        c.gridwidth = 1;
        c.weightx = 0.9;
        c.weighty = 0.2;
        c.gridx = 0;
        c.gridy = 1;
        chatWindow.add(new JScrollPane(messageToSendTextArea), c);

        sendMessageButton = new JButton("Send");
        c.weightx = 0.1;
        c.gridx = 1;
        c.gridy = 1;
        chatWindow.add(sendMessageButton, c);

        sendMessageButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(currentConversation!=null){
                    client.sendMessage(currentConversation.getId(), messageToSendTextArea.getText());
                    addMessageToConversation(new Message(client.getUsername(), currentConversation.getId(), messageToSendTextArea.getText(), System.currentTimeMillis()));
                    messageToSendTextArea.setText("");
                }
            }
        });
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new CardLayout()); // set card layout
        mainPanel.add(loginPanel, "loginPanelCard");
        mainPanel.add(registerPanel, "registerPanelCard");
        mainPanel.add(chatPanel, "chatPanelCard");

        setContentPane(mainPanel);
    }

    //endregion

    //region Functional

    private void login(){
        String username = loginUsernameTextField.getText();
        String password = new String(loginPasswordTextField.getPassword()).trim();

        client.serverRequest("POST /login?username="+username+"&password="+password, serverResponse -> {

            //Login was successfull, load everything needed to proceed
            if(serverResponse.getStatusCode().equals(StatusCode.OK)){

                client.setUsername(username);
                client.populateFriendList();


                changeCard("chatPanelCard");
            }
            else{
                JOptionPane.showMessageDialog(null, serverResponse.getResponse().toString());
            }

            return null;
        });
    }

    private void register(){
        String username = registerUsernameText.getText().trim();
        String password = new String(registerPasswordField.getPassword()).trim();
        String passwordCheck = new String(registerPasswordFieldCheck.getPassword()).trim();
        String email = registerEmailField.getText();

        boolean valid = true;
        String errorMessage = "";

        // Conditions to fail
        if(username.length() == 0){
            valid = false;
            errorMessage = "Username field can't be empty!";
        }

        else if(!password.equals(passwordCheck)){
            valid = false;
            errorMessage = "Passwords are not the same!";
        }

        if(valid){
            client.serverRequest("POST /user/create?username="+username+"&password="+password+"&email="+email, serverResponse -> {
                if(serverResponse.getStatusCode().equals(StatusCode.OK)){
                    JOptionPane.showMessageDialog(null, serverResponse.getResponse().toString());
                    changeCard("loginPanelCard");
                }
                else{
                    JOptionPane.showMessageDialog(null, serverResponse.getResponse().toString());
                }

                return null;
            });
        }
    }

    private void switchCurrentConversation(){
        client.getConversation(Extras.Utilities.CreateConversationID(client.getUsername(), ((Client.Friend)friendListModel.get(friendsList.getSelectedIndex())).username));
    }

    //endregion

    //region Observer Functions

    @Override
    public void friendRequestAccepted(Object o) {
        Client.Friend f = (Client.Friend)o;
        for(int i = 0; i < friendListModel.size(); i++){
            Client.Friend temp = ((Client.Friend)friendListModel.get(i));
            if(temp.username.equals(f.username)){
                friendListModel.remove(i);
                break;
            }
        }
        friendListModel.addElement(f);
    }

    @Override
    public void newReceivedFriendRequest(Object o) {
        friendListModel.addElement((Client.Friend)o);
    }

    @Override
    public void newSentFriendRequest(Object o) {
        friendListModel.addElement((Client.Friend)o);
    }

    @Override
    public void updateFriendList(Object o) {
        for(Client.Friend friend : client.getFriends()){
            friendListModel.addElement(friend);
        }
    }

    @Override
    public void removedFriend(Object o) {
        Client.Friend f  = (Client.Friend)o;
        for(int i = 0; i < friendListModel.size(); i++){
            Client.Friend temp = ((Client.Friend)friendListModel.get(i));
            if(temp.username.equals(f.username)){
                friendListModel.remove(i);
                break;
            }
        }
    }

    @Override
    public void changeCurrentConversation(Conversation c) {
        currentConversation = c;
        conversationTextArea.setText("");
        for(Message m : c.getMessages()){
            addMessageToConversation(m);
        }
    }

    @Override
    public void newMessage(int convID, Message m) {
        if(currentConversation!=null){
            if(currentConversation.getId()==convID){
                addMessageToConversation(m);
            }
        }

    }

    @Override
    public void openSecretConversation(String ip) {
        PrivateChat pv = new PrivateChat(client, ip);
    }

    //endregion

    private void addMessageToConversation(Message m){
        conversationTextArea.setText(conversationTextArea.getText()+m.getSender()+": " + m.getContent() +  "\n=============\n");
    }


    private void changeCard(String cardName){
        CardLayout cl = (CardLayout)mainPanel.getLayout();
        cl.show(mainPanel, cardName);
    }

    //region Variables
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel chatPanel;
    private JPanel registerPanel;

    //Fonts
    private Font bigFont;

    //registerPanel variables
    private JTextField registerUsernameText;
    private JPasswordField registerPasswordField;
    private JPasswordField registerPasswordFieldCheck;
    private JTextField registerEmailField;
    private JButton registerConfirmButton;

    //loginPanel variables
    private JTextField loginUsernameTextField;
    private JPasswordField loginPasswordTextField;
    private JButton loginButton;
    private JButton registerButton;

    //chatPanel variables
    private JPanel friendsPanel;
    private JPanel chatWindow;
    private JList<Client.Friend> friendsList;
    private JTextField friendSearchText;
    private JTextArea conversationTextArea;
    private JTextArea messageToSendTextArea;
    private JButton sendMessageButton;
    private JButton addFriendButton;

    //Friend List Options
    private JPopupMenu friendListPopupMenu;
    private JMenuItem acceptFriendRequest;
    private JMenuItem rejectFriendRequest;
    private JMenuItem removeFriend;
    private JMenuItem startPrivateConversation;
    private JMenuItem startConversation;


    //Top Menu Bar variables
    private JMenuBar menuBar;
    //endregion

    public static void main(String[] args) {
        Application app = new Application();
        app.setVisible(true);
    }
}
