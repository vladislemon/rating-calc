package gui;

import main.Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * slimon
 * 07.07.2014
 */
public class GuiChat extends JPanel {

    public JTabbedPane tabbedPane;
    public JPanel chatPanel, optionsPanel, enterPanel, messagesPanel;
    public JTextField enterField, ipField, portField, nickField;
    public JTextArea messagesField;
    public JScrollPane scrollPane;
    public JButton sendButton, hostButton, connectButton;

    public GuiChat() {
        this.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        enterPanel = new JPanel();
        enterPanel.setLayout(new BoxLayout(enterPanel, BoxLayout.LINE_AXIS));
        messagesPanel = new JPanel();
        messagesPanel.setLayout(new BorderLayout());

        enterField = new JTextField();
        messagesField = new JTextArea();
        messagesField.setLineWrap(true);
        messagesField.setEditable(false);
        //messagesField.setForeground(Color.blue);
        scrollPane = new JScrollPane(messagesField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sendButton = new JButton("Отправить");
        //messagesField.append("dgdfjgh\nfgdfg\ndfgs4\n3343\n\n344\n\ndfg45234wtfwt\n4w3tg\n54w\n54w");

        //========================================================

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());

        nickField = new JTextField();
        nickField.setPreferredSize(new Dimension(200, 50));
        ipField = new JTextField();
        ipField.setPreferredSize(new Dimension(200, 50));
        portField = new JTextField();
        portField.setPreferredSize(new Dimension(200, 50));
        hostButton = new JButton("host");
        connectButton = new JButton("connect");


        linkComponents();
        initListeners();
    }

    private void linkComponents() {
        this.add(tabbedPane);

        tabbedPane.addTab("", chatPanel);
        tabbedPane.addTab("Настройки", optionsPanel);

        chatPanel.add(messagesPanel, BorderLayout.CENTER);
        messagesPanel.add(scrollPane, BorderLayout.CENTER);
        chatPanel.add(enterPanel, BorderLayout.SOUTH);
        enterPanel.add(enterField);
        enterPanel.add(sendButton);

        //========================

        optionsPanel.add(nickField);
        optionsPanel.add(ipField);
        optionsPanel.add(portField);
        ipField.setText("127.0.0.1");
        portField.setText("45323");
        optionsPanel.add(hostButton);
        optionsPanel.add(connectButton);
    }

    private void initListeners() {
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Chat.isHosted) {
                    Chat.hostServer();
                } else {
                    Chat.closeServer();
                }
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Chat.isConnected) {
                    Chat.connectToServer();
                    messagesField.append(Chat.client.getConnection().getStatus().name());
                } else {
                    Chat.disconnect();
                    messagesField.append(Chat.client.getConnection().getStatus().name());
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = enterField.getText();
                if(text != null && !text.equals("")) {
                    Chat.sendChatMessage(text);
                    enterField.setText("");
                }
            }
        });
    }
}
