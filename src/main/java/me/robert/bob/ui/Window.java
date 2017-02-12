package me.robert.bob.ui;

import com.google.gson.JsonParser;
import me.robert.bob.BubblesOfficialBot;
import me.robert.bob.files.Configuration;
import me.robert.bob.ui.tabs.TabChat;
import me.robert.bob.ui.tabs.TabChatTab;
import me.robert.bob.utils.HTTPRequest;
import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class Window {

    private JFrame jFrame;
    private SpringLayout springLayout;
    private JTabbedPane tabbedPane;

    private JMenuBar menuBar;
    // Settings
    private JMenu settings;
    private JMenuItem chatTimeStamps;
    private JMenuItem openConfig;

    // Chat
    private JMenu chat;
    private JMenuItem chatJoin;
    private JMenuItem chatPart;
    private JMenuItem chatClear;

    private BubblesOfficialBot bot;

    private JsonParser jsonParser;

    /**
     * TABS
     **/
    private TabChatTab chatTab;

    private Dimension size = new Dimension(1280, 720);

    public Window(BubblesOfficialBot bot) {
        this.bot = bot;

        this.jFrame = new JFrame("Bubbles Official Bot");
        this.jFrame.setDefaultLookAndFeelDecorated(true);
        this.springLayout = new SpringLayout();

        this.jFrame.getContentPane().setLayout(this.springLayout);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setMinimumSize(size);
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.pack();

        this.initialize();
    }

    private void initialize() {
        this.jsonParser = new JsonParser();

        /** Main Tab **/

        this.menuBar = new JMenuBar();
        // Settings
        this.settings = new JMenu("Settings");

        this.chatTimeStamps = new JMenuItem("Toggle timestamps for chat.");
        this.chatTimeStamps.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Toggle timestamps for chat."))
                Configuration.getConfiguration().set("chatTimestampsEnabled", !Configuration.getConfiguration().getBoolean("chatTimestampsEnabled"));
        });

        this.openConfig = new JMenuItem("Open the config file for the bot.");
        this.openConfig.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Open the config file for the bot.")) {
                try {
                    Desktop.getDesktop().open(Configuration.getConfiguration().getFile());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        this.settings.add(this.chatTimeStamps);
        this.settings.add(this.openConfig);
        this.menuBar.add(settings);

        // Chat
        this.chat = new JMenu("Chat");
        this.chatJoin = new JMenuItem("Join a channel.");
        this.chatJoin.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Join a channel.")) {
                String channel = JOptionPane.showInputDialog(null, "What channel would you like to join?", "o3bubbles09");
                getChatTab().addChannel(channel, new TabChat(
                        this.bot.getChannelManager().addChannel(channel)));
                HTTPRequest.startUptime(channel);
            }
        });
        this.chat.add(this.chatJoin);

        this.chatPart = new JMenuItem("Part a channel.");
        this.chatPart.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Part a channel.")) {
                TabChat temp = (TabChat) getChatTab().getTabbedPane().getSelectedComponent();
                getChatTab().removeChannel(temp.getChannelName());
            }
        });
        this.chat.add(this.chatPart);

        this.menuBar.add(this.chat);

        this.chatClear = new JMenuItem("Clear chat.");
        this.chatClear.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Clear chat.")) {
                TabChat temp = (TabChat) getChatTab().getTabbedPane().getSelectedComponent();
                bot.getChannelManager().getChannel(temp.getChannelName()).messageChannel("/clear");
            }
        });
        this.chat.add(this.chatClear);

        this.jFrame.getContentPane().add(menuBar);

        this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        this.springLayout.putConstraint(SpringLayout.NORTH, this.tabbedPane, 10, SpringLayout.SOUTH, this.menuBar);
        this.springLayout.putConstraint(SpringLayout.EAST, this.tabbedPane, -10, SpringLayout.EAST, this.jFrame.getContentPane());
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.tabbedPane, -10, SpringLayout.SOUTH, this.jFrame.getContentPane());
        this.springLayout.putConstraint(SpringLayout.WEST, this.tabbedPane, 10, SpringLayout.WEST, this.jFrame.getContentPane());
        this.jFrame.getContentPane().add(tabbedPane);
        /** Main Tab **/

        /** Chat Tab **/
        this.chatTab = new TabChatTab(this.bot);
        this.tabbedPane.addTab("Chat", this.chatTab);
        /** Chat Tab **/

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceMagmaLookAndFeel());
            } catch (Exception e) {
                System.out.println("Substance Graphite failed to initialize");
            }
            jFrame.setVisible(true);
            SwingUtilities.updateComponentTreeUI(jFrame);
        });
    }

    public TabChatTab getChatTab() {
        return this.chatTab;
    }
}
