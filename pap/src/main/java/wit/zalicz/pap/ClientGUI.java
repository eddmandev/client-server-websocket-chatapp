package wit.zalicz.pap;

import io.micrometer.common.util.StringUtils;
import wit.zalicz.pap.clients.MessageListener;
import wit.zalicz.pap.clients.StompClient;
import wit.zalicz.pap.model.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static wit.zalicz.pap.utils.ComponentUtilities.*;

public class ClientGUI extends JFrame implements MessageListener {
    private JPanel connectedUsersPanel;
    private JPanel messagePanel;
    private StompClient client;
    private String username;

    public ClientGUI(String username) throws ExecutionException, InterruptedException {
        super("User: " + username);
        this.username = username;
        client = new StompClient(this, username);
        setSize(1218, 685);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int userChoice = JOptionPane.showConfirmDialog(ClientGUI.this, "Czy chcesz wyjść z aplikacji?", "Zamknij", JOptionPane.YES_NO_OPTION);
                if (userChoice == JOptionPane.YES_OPTION) {
                    client.disconnectUser(username);
                    ClientGUI.this.dispose();
                }
            }
        });

        getContentPane().setBackground(WIT_LIGHT_GRAY);
        setLayout(new BorderLayout());

        add(createHeaderPanel(), BorderLayout.NORTH);
        addGuiComponents();
        addChatComponents();
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(getWidth(), 40));
        header.setBackground(WIT_RED);

        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/static/wit-logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBorder(addPadding(4, 10, 4, 10));
        logoLabel.setVerticalAlignment(SwingConstants.TOP);
        logoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        leftPanel.setBackground(WIT_RED);
        leftPanel.add(logoLabel);

        JLabel title = new JLabel("Akademia WIT – Chat");
        title.setForeground(TEXT_COLOR);
        title.setFont(new Font("Inter", Font.BOLD, 18));
        title.setBorder(addPadding(0, 10, 0, 0));

        header.add(leftPanel, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);

        return header;
    }


    private void addGuiComponents() {
        addConnectedUsersComponents();
    }

    private void addConnectedUsersComponents() {
        connectedUsersPanel = new JPanel();
        connectedUsersPanel.setBorder(addPadding(10, 10, 10, 10));
        connectedUsersPanel.setLayout(new BoxLayout(connectedUsersPanel, BoxLayout.Y_AXIS));
        connectedUsersPanel.setBackground(WIT_DARK_GRAY);
        connectedUsersPanel.setPreferredSize(new Dimension(200, getHeight()));

        var connectedUsersLabel = new JLabel("Online:");
        connectedUsersLabel.setFont(new Font("Inter", Font.BOLD, 18));
        connectedUsersLabel.setForeground(TEXT_COLOR);
        connectedUsersPanel.add(connectedUsersLabel);

        add(connectedUsersPanel, BorderLayout.WEST);
    }

    private void addChatComponents() {
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(WIT_LIGHT_GRAY);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(WIT_DARK_GRAY);
        chatPanel.add(new JScrollPane(messagePanel), BorderLayout.CENTER);

        var inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(WIT_DARK_GRAY);
        inputPanel.setBorder(addPadding(10, 10, 10, 10));

        var inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String input = inputField.getText();
                    if (StringUtils.isEmpty(input)) return;
                    inputField.setText("");
                    client.sendMessage(new Message(username, input));
                }
            }
        });

        inputField.setBackground(WIT_DARK_GRAY);
        inputField.setForeground(TEXT_COLOR);
        inputField.setBorder(BorderFactory.createLineBorder(WIT_RED, 2));
        inputField.setFont(new Font("Inter", Font.PLAIN, 16));
        inputField.setCaretColor(TEXT_COLOR);
        inputField.setPreferredSize(new Dimension(0, 50));

        inputPanel.add(inputField, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        add(chatPanel, BorderLayout.CENTER);
    }

    private JPanel createChatMessageComponent(Message message) {
        var chatMessage = new JPanel();
        chatMessage.setBackground(TRANSPARENT);
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBorder(addPadding(20, 20, 10, 20));
        chatMessage.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        var usernameLabel = new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.BOLD, 18));
        usernameLabel.setForeground(TEXT_COLOR);
        chatMessage.add(usernameLabel);

        var messageLabel = new JLabel(message.getMessage());
        messageLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        messageLabel.setForeground(TEXT_COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    @Override
    public void onMessageReceived(Message message) {
        messagePanel.add(Box.createVerticalStrut(5));
        messagePanel.add(createChatMessageComponent(message));
        messagePanel.add(Box.createVerticalStrut(5));
        revalidate();
        repaint();
    }

    @Override
    public void onActiveUsersUpdated(ArrayList<String> users) {
        if (connectedUsersPanel == null) return;

        while (connectedUsersPanel.getComponentCount() > 1) {
            connectedUsersPanel.remove(1);
        }

        var userListPanel = new JPanel();
        userListPanel.setBackground(TRANSPARENT);
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        users.forEach(user -> {
            var username = new JLabel(user);
            username.setForeground(TEXT_COLOR);
            username.setFont(new Font("Inter", Font.BOLD, 16));
            userListPanel.add(username);
        });

        connectedUsersPanel.add(userListPanel);
        connectedUsersPanel.revalidate();
        connectedUsersPanel.repaint();
    }
}
