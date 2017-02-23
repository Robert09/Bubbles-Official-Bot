package me.robert.bob.ui;

import me.robert.bob.Bot;
import me.robert.bob.Launch;
import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by O3Bubbles09 on 2/14/2017
 **/
public class Window {

    private JFrame jFrame;
    private SpringLayout springLayout;

    private JMenuBar menuBar;
    private JMenu settings;
    private JMenuItem toggleConsole;
    private JMenuItem joinChannel;
    private JMenuItem partChannel;

    private final int WIDTH = 720;
    private final int HEIGHT = WIDTH / 16 * 9;
    private Dimension size = new Dimension(WIDTH, HEIGHT);

    private Console console;
    private ChatPage chatPage;

    public Window() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        this.jFrame = new JFrame("Bubble's Bot");
        this.springLayout = new SpringLayout();

        this.jFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/me.robert.bob.ui/logo.png")));

        this.jFrame.getContentPane().setLayout(this.springLayout);
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jFrame.setMinimumSize(size);
        this.jFrame.setLocationRelativeTo(null);
        this.jFrame.pack();

        this.initialize();
    }

    private void initialize() {
        this.console = new Console();
        this.chatPage = new ChatPage();

        /** Main Tab **/

        this.menuBar = new JMenuBar();
        // Settings
        this.settings = new JMenu("Settings");

        this.toggleConsole = new JMenuItem("Toggle console.");
        this.toggleConsole.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Toggle console.")) {
                this.console.toggleVisible();
            }
        });
        this.settings.add(this.toggleConsole);

        this.joinChannel = new JMenuItem("Join channel.");
        this.joinChannel.addActionListener(e -> {
            if (e.getActionCommand().equalsIgnoreCase("Join channel.")) {
                String channel = JOptionPane.showInputDialog("What channel would you like to join?");
                if (!channel.isEmpty())
                    this.chatPage.joinChannel(channel);
            }
        });
        this.settings.add(this.joinChannel);
//
//        this.partChannel = new JMenuItem("Part channel.");
//        this.partChannel.addActionListener(e -> {
//            if (e.getActionCommand().equalsIgnoreCase("Part channel.")) {
//                this.chatPage.partChannel();
//            }
//        });
//        this.settings.add(this.partChannel);

        this.menuBar.add(this.settings);
        this.jFrame.getContentPane().add(menuBar);

        this.springLayout.putConstraint(SpringLayout.NORTH, this.chatPage, 10, SpringLayout.SOUTH, this.menuBar);
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
