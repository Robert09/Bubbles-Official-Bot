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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/14/2017
 **/
public class ChatPage extends JPanel {

    private SpringLayout springLayout;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JTextField textField;

    private JScrollPane viewerPane;
    private JTextPane viewerTextArea;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
    private Date date = new Date();

    private StyledDocument document;
    private StyledDocument viewerDoc;

    private Style color;
    private Style viewerCol;
    private Style baseCol;

    private Color orange = new Color(255, 69, 0);

    public ChatPage() {
        this.springLayout = new SpringLayout();
        this.scrollPane = new JScrollPane();
        this.textPane = new JTextPane();
        this.textField = new JTextField();
        this.textField.setForeground(orange);
        this.textField.setText("Enter a message to send.");
        this.textField.addFocusListener(new FocusListener());

        this.viewerPane = new JScrollPane();
        this.viewerTextArea = new JTextPane();
        this.viewerTextArea.setEditable(false);

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

        this.viewerDoc = this.viewerTextArea.getStyledDocument();
        this.viewerCol = this.viewerTextArea.addStyle("UserCol", null);
        this.baseCol = this.viewerTextArea.addStyle("BaseCol", null);
        StyleConstants.setForeground(this.viewerCol, orange);
        StyleConstants.setForeground(this.baseCol, orange);
        StyleConstants.setBold(this.viewerCol, true);
        StyleConstants.setBold(this.baseCol, true);

        this.document = this.textPane.getStyledDocument();
        this.color = this.textPane.addStyle("Color", null);
        StyleConstants.setForeground(this.color, Color.red);

        this.viewerPane.setViewportView(this.viewerTextArea);

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

        this.add(this.textField);
        this.add(this.viewerPane);
        this.add(this.scrollPane);
    }

    public void joinChannel(String channel) {
        Launch.getInstance().setBot(new Bot(channel));
        Launch.getInstance().getBot().setSettingsFile(new SettingsFile("settings.yaml"));
    }

    public void messageChannel() {
        String message = this.textField.getText();
        this.textField.setText("");
        appendChat(Launch.USER, Color.CYAN, message);
        Launch.getInstance().getBot().getChannel().messageChannel(message);
    }

    public void updateViewers(List<User> users) {
        this.viewerTextArea.setText("");

        for (User user : users) {
            StyleConstants.setForeground(this.viewerCol, user.getColor());

            try {
                if (user.isMod())
                    this.viewerDoc.insertString(this.viewerDoc.getLength(), "[MOD] \r", this.baseCol);
                this.viewerDoc.insertString(this.viewerDoc.getLength(), String.format("%s\r\n", user.getName()), this.viewerCol);

            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
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
