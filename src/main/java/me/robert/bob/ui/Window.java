package me.robert.bob.ui;

import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by O3Bubbles09 on 2/14/2017
 **/
public class Window {

    private JFrame jFrame;
    private SpringLayout springLayout;

    private Console console;
    private ChatPage chatPage;

    public Window() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.jFrame = new JFrame("Bubble's Bot");
        this.springLayout = new SpringLayout();

        this.jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/me.robert.bob.ui/logo.png")));

        this.jFrame.getContentPane().setLayout(this.springLayout);
        this.jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        int WIDTH = 720;
        int HEIGHT = WIDTH / 16 * 9;
        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.jFrame.setMinimumSize(size);
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.pack();

        this.initialize();
    }

    private void initialize() {
        this.console = new Console();
        this.chatPage = new ChatPage();

        JMenuBar menuBar = new JMenuBar();
        // Settings
        JMenu settings = new JMenu("Settings");

        JMenuItem toggleConsole = new JMenuItem("Toggle console.");
        toggleConsole.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Toggle console.")) {
                this.console.toggleVisible();
            }
        });
        settings.add(toggleConsole);

        JMenuItem joinChannel = new JMenuItem("Join channel.");
        joinChannel.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Join channel.")) {
                String channel = JOptionPane.showInputDialog("What channel would you like to join?");
                if (!channel.isEmpty())
                    this.chatPage.joinChannel(channel);
            }
        });
        settings.add(joinChannel);

        JMenuItem partChannel = new JMenuItem("Part channel.");
        partChannel.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Part channel.")) {
                this.chatPage.partChannel();
            }
        });
        settings.add(partChannel);

        menuBar.add(settings);
        this.jFrame.getContentPane().add(menuBar);

        this.springLayout.putConstraint(SpringLayout.NORTH, this.chatPage, 10, SpringLayout.SOUTH, menuBar);
        this.springLayout.putConstraint(SpringLayout.EAST, this.chatPage, -10, SpringLayout.EAST, this.jFrame.getContentPane());
        this.springLayout.putConstraint(SpringLayout.SOUTH, this.chatPage, -10, SpringLayout.SOUTH, this.jFrame.getContentPane());
        this.springLayout.putConstraint(SpringLayout.WEST, this.chatPage, 10, SpringLayout.WEST, this.jFrame.getContentPane());

        this.jFrame.getContentPane().add(this.chatPage);

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

    public Console getConsole() {
        return this.console;
    }

    public ChatPage getChatPage() {
        return this.chatPage;
    }
}
