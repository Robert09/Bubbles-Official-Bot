package me.robert.bob.ui;

import org.jvnet.substance.skin.SubstanceMagmaLookAndFeel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by O3Bubbles09 on 2/14/2017
 **/
public class Console {

    private JFrame frame;

    private JScrollPane scrollPane;
    private JTextArea textArea;

    Console() {
        this.frame = new JFrame("Console");

        this.frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/me.robert.bob.ui/logo.png")));

        SpringLayout layout = new SpringLayout();

        this.frame.getContentPane().setLayout(layout);
        int width = 720;
        int height = width / 16 * 9;
        Dimension size = new Dimension(width, height);
        this.frame.getContentPane().setPreferredSize(size);
        this.frame.pack();

        this.scrollPane = new JScrollPane();
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setForeground(new Color(255, 69, 0));

        this.scrollPane.setViewportView(this.textArea);

        layout.putConstraint(SpringLayout.NORTH, this.scrollPane, 10, SpringLayout.NORTH, this.frame.getContentPane());
        layout.putConstraint(SpringLayout.EAST, this.scrollPane, -10, SpringLayout.EAST, this.frame.getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, this.scrollPane, -10, SpringLayout.SOUTH, this.frame.getContentPane());
        layout.putConstraint(SpringLayout.WEST, this.scrollPane, 10, SpringLayout.WEST, this.frame.getContentPane());

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

    void toggleVisible() {
        this.frame.setVisible(!this.frame.isVisible());
    }
}
