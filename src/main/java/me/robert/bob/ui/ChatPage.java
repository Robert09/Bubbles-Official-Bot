package me.robert.bob.ui;

import me.robert.bob.Bot;
import me.robert.bob.Launch;
import me.robert.bob.files.SettingsFile;
import me.robert.irc.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by O3Bubbles09 on 2/14/2017
 **/
public class ChatPage extends JPanel {

    private SpringLayout springLayout;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JTextField textField;

    private JScrollPane viewerPane;
    private JPanel viewerPanel;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
    private Date date = new Date();

    private StyledDocument document;

    private Style color;

    private Color orange = new Color(255, 69, 0);

    private JLabel[] viewers;

    public ChatPage() {
        this.springLayout = new SpringLayout();
        this.scrollPane = new JScrollPane();
        this.textPane = new JTextPane();
        this.textField = new JTextField();
        this.textField.setForeground(orange);
        this.textField.setText("Enter a message to send.");
        this.textField.addFocusListener(new FocusListener());

        this.viewerPane = new JScrollPane();
        this.viewerPanel = new JPanel();
        this.viewerPanel.setLayout(new BoxLayout(this.viewerPanel, BoxLayout.Y_AXIS));

        this.setLayout(this.springLayout);

        this.springLayout.putConstraint(SpringLayout.EAST, this.textField, -10, SpringLayout.EAST, this);
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.textField, -10, SpringLayout.SOUTH, this);
        this.springLayout.putConstraint(SpringLayout.WEST, this.textField, 10, SpringLayout.WEST, this);

        this.springLayout.putConstraint(SpringLayout.NORTH, this.viewerPane, 10, SpringLayout.NORTH, this);
        this.springLayout.putConstraint(SpringLayout.EAST, this.viewerPane, -10, SpringLayout.EAST, this);
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.viewerPane, -10, SpringLayout.NORTH, this.textField);
        this.springLayout.putConstraint(SpringLayout.WEST, this.viewerPane, -200, SpringLayout.EAST, this);

        this.springLayout.putConstraint(SpringLayout.NORTH, this.scrollPane, 10, SpringLayout.NORTH, this);
        this.springLayout.putConstraint(SpringLayout.EAST, this.scrollPane, -10, SpringLayout.WEST, this.viewerPane);
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.scrollPane, -10, SpringLayout.NORTH, this.textField);
        this.springLayout.putConstraint(SpringLayout.WEST, this.scrollPane, 10, SpringLayout.WEST, this);

        this.textPane.setEditable(false);

        this.document = this.textPane.getStyledDocument();
        this.color = this.textPane.addStyle("Color", null);
        StyleConstants.setForeground(this.color, Color.red);

        this.scrollPane.setViewportView(this.textPane);

        this.textField.requestFocus();

        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    messageChannel();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.viewerPane.setViewportView(this.viewerPanel);

        this.add(this.textField);
        this.add(this.viewerPane);
        this.add(this.scrollPane);
    }

    public void joinChannel(String channel) {
        Launch.getInstance().setBot(new Bot(channel));
        Launch.getInstance().getBot().setSettingsFile(new SettingsFile("settings.yaml"));
    }

    public void partChannel() {
        Launch.getInstance().getBot().getChannel().part();
    }

    public void messageChannel() {
        String message = this.textField.getText();
        this.textField.setText("");
        appendChat(Launch.USER, Color.CYAN, message);
        Launch.getInstance().getBot().getChannel().messageChannel(message);
    }

    public void updateViewers(LinkedList<User> users) {
        this.viewers = new JLabel[Launch.getInstance().getBot().getChannel().getUsers().size()];

        for (Component component : this.viewerPanel.getComponents()) {
            this.viewerPanel.remove(component);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < Launch.getInstance().getBot().getChannel().getUsers().size(); i++) {
                    User user = users.get(i);

                    JLabel temp = new JLabel("");
                    if (user.isMod()) {
                        temp.setForeground(user.getColor());
                        temp.setText("[MOD] " + user.getName());
                        viewers[i] = temp;
                        viewerPanel.add(viewers[i]);
                    } else {
                        temp.setForeground(user.getColor());
                        temp.setText(user.getName());
                        viewers[i] = temp;
                        viewerPanel.add(viewers[i]);
                    }

                    viewers[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            onMouseClicked(e);
                        }
                    });

                    viewerPanel.validate();
                    viewerPanel.repaint();
                }

            }
        });

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

    private String[] commands = new String[]{"", "Ban", "Un-Ban", "Timeout", "Un-Timeout"};

    private void onMouseClicked(MouseEvent e) {
        for (int i = 0; i < this.viewers.length; i++) {
            if (e.getSource() == this.viewers[i]) {

                String selectedCmd = (String) JOptionPane.showInputDialog(this,
                        "What action would you like to perform on this user?",
                        "Action to perform",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        commands,
                        commands[0]);

                switch (selectedCmd) {
                    case "":
                        break;
                    case "Ban":
                        Launch.getInstance().getBot().getChannel().messageChannel("/ban " + this.viewers[i]
                                .getText());
                        break;
                    case "Un-Ban":
                        Launch.getInstance().getBot().getChannel().messageChannel("/unban " + this.viewers[i]
                                .getText());
                        break;
                    case "Timeout":
                        Launch.getInstance().getBot().getChannel().messageChannel("/timeout " + this.viewers[i]
                                .getText());
                        break;
                    case "Un-Timeout":
                        Launch.getInstance().getBot().getChannel().messageChannel("/untimeout " + this.viewers[i]
                                .getText());
                        break;
                }
            }
        }
    }

    public void appendChat(String sender, Color c, String message) {
        StyleConstants.setForeground(this.color, c);
        StyleConstants.setBold(this.color, true);
        try {
            this.document.insertString(this.document.getLength(), String.format("[%s][%s]: \r", dateFormat.format(date), sender), this.color);
            StyleConstants.setForeground(this.color, Color.RED);
            StyleConstants.setBold(this.color, false);
            this.document.insertString(this.document.getLength(), message + "\r\n", this.color);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }
}
