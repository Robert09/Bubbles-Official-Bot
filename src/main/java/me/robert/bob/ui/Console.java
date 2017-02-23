package me.robert.bob.ui;

import clojure.lang.Cons;
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
public class Console {

    private JFrame frame;
    private SpringLayout layout;

    private int width = 720;
    private int height = width / 16 * 9;

    private Dimension size = new Dimension(width, height);

    private JScrollPane scrollPane;
    private JTextArea textArea;

    public Console() {
        this.frame = new JFrame("Console");

        this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/me.robert.bob.ui/logo.png")));

        this.layout = new SpringLayout();

        this.frame.getContentPane().setLayout(layout);
        this.frame.getContentPane().setPreferredSize(size);
        this.frame.pack();

        this.scrollPane = new JScrollPane();
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setForeground(new Color(255, 69, 0));

        this.scrollPane.setViewportView(this.textArea);

        this.layout.putConstraint(SpringLayout.NORTH, this.scrollPane, 10, SpringLayout.NORTH, this.frame.getContentPane());
        this.layout.putConstraint(SpringLayout.EAST, this.scrollPane, -10, SpringLayout.EAST, this.frame.getContentPane());
        this.layout.putConstraint(SpringLayout.SOUTH, this.scrollPane, -10, SpringLayout.SOUTH, this.frame.getContentPane());
        this.layout.putConstraint(SpringLayout.WEST, this.scrollPane, 10, SpringLayout.WEST, this.frame.getContentPane());

        this.frame.getContentPane().add(this.scrollPane);

        this.frame.setLocationRelativeTo(null);

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceMagmaLookAndFeel());
            } catch (Exception e) {
                System.out.println("Substance Graphite failed to initialize");
            }
            SwingUtilities.updateComponentTreeUI(frame);
        });
    }

    public void log(String message) {
        textArea.append(message + "\n");
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }

    public void toggleVisible() {
        this.frame.setVisible(!this.frame.isVisible());
    }
}
