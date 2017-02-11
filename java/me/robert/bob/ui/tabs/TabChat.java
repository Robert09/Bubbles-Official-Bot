package me.robert.bob.ui.tabs;

import me.robert.bob.Launch;
import me.robert.bob.files.Configuration;
import me.robert.bob.utils.ClearChat;
import me.robert.ircb.Channel;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class TabChat extends JPanel {

    private SpringLayout springLayout;
    private JScrollPane scrollPane;
    private JTextPane textPane;
    private JTextField textField;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
    private Date date = new Date();

    private StyledDocument document;

    private Style color;

    private Channel channel;

    private String channelName;

    private ClearChat clearChat;

    public TabChat(Channel channel) {
        this.channel = channel;
        this.channelName = channel.getName();
        this.springLayout = new SpringLayout();
        this.scrollPane = new JScrollPane();
        this.textPane = new JTextPane();
        this.textField = new JTextField();

        this.setLayout(this.springLayout);

        this.springLayout.putConstraint(SpringLayout.EAST, this.textField, -10, SpringLayout.EAST, this);
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.textField, -10, SpringLayout.SOUTH, this);
        this.springLayout.putConstraint(SpringLayout.WEST, this.textField, 10, SpringLayout.WEST, this);

        this.springLayout.putConstraint(SpringLayout.NORTH, this.scrollPane, 10, SpringLayout.NORTH, this);
        this.springLayout.putConstraint(SpringLayout.EAST, this.scrollPane, -10, SpringLayout.EAST, this);
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

        this.add(this.textField);
        this.add(this.scrollPane);

        this.clearChat = new ClearChat(channel);
    }

    public ClearChat getClearChat() {
        return this.clearChat;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public void messageChannel() {
        String message = this.textField.getText();
        this.textField.setText("");
        appendChat((String) Configuration.getLogin().get("username"), Color.CYAN, message);
        Launch.getInstance().getBot().getChannelManager().getChannel(this.channelName).messageChannel(message);
    }

    public void appendChat(String sender, Color c, String message) {
        StyleConstants.setForeground(this.color, c);
        StyleConstants.setBold(this.color, true);
        try {
            if (Configuration.getConfiguration().getBoolean("chatTimestampsEnabled"))
                this.document.insertString(this.document.getLength(), String.format("[%s][%s]: \r", dateFormat.format(date), sender), this.color);
            else
                this.document.insertString(this.document.getLength(), String.format("[%s]: \r", sender), this.color);
            StyleConstants.setForeground(this.color, Color.RED);
            StyleConstants.setBold(this.color, false);
            this.document.insertString(this.document.getLength(), message + "\r\n", this.color);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }
}
