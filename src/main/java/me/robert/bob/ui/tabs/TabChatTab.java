package me.robert.bob.ui.tabs;

import me.robert.bob.BubblesOfficialBot;
import me.robert.bob.Launch;

import javax.security.auth.callback.LanguageCallback;
import javax.swing.*;
import java.util.HashMap;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class TabChatTab extends JPanel {

    private JTabbedPane tabbedPane;
    private JTextField textField;
    private SpringLayout springLayout;

    private BubblesOfficialBot bot;

    private HashMap<String, TabChat> chatTabs;

    public TabChatTab(BubblesOfficialBot bot) {
        this.bot = bot;
        this.chatTabs = new HashMap<String, TabChat>();
        this.springLayout = new SpringLayout();

        this.setLayout(this.springLayout);

        this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        this.springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, this);
        this.springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -10, SpringLayout.EAST, this);
        this.springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -10, SpringLayout.SOUTH, this);
        this.springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, this);
        this.add(tabbedPane);

        this.textField = new JTextField();
    }

    public HashMap<String, TabChat> getTabs() {
        return this.chatTabs;
    }

    public void addChannel(String channel, TabChat tab) {
        this.chatTabs.put(channel, tab);
        this.tabbedPane.addTab(channel, tab);
    }

    public void removeChannel(String channel) {
        this.tabbedPane.remove(this.chatTabs.get(channel));
        this.chatTabs.remove(channel);
        this.bot.getChannelManager().removeChannel(channel);
    }

    public JTabbedPane getTabbedPane() {
        return this.tabbedPane;
    }

    public TabChat getChat(String channel) {
        return this.chatTabs.get(channel);
    }
}
