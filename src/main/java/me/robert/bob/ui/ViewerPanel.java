package me.robert.bob.ui;

import me.robert.bob.Launch;
import me.robert.irc.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/27/2017
 **/
public class ViewerPanel extends JPanel {

    private List<JLabel> viewers;

    public ViewerPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void updateViewers(List<User> users) {
        this.viewers = new ArrayList<>();

        for (Component component : this.getComponents()) {
            this.remove(component);
        }

        SwingUtilities.invokeLater(() -> {
            for (User user : users) {
                JLabel temp = new JLabel("", SwingConstants.CENTER);
                Font font = temp.getFont();
                Font bold = new Font(font.getFontName(), Font.BOLD, font.getSize());
                temp.setFont(bold);
                temp.setOpaque(true);
                temp.setForeground(user.getColor());
                temp.setAlignmentX(Component.CENTER_ALIGNMENT);

                if (user.isMod()) {
                    temp.setText("[MOD] " + user.getName());
                } else {
                    temp.setText(user.getName());
                }

                viewers.add(temp);
                temp.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        onMouseClicked(e);
                    }
                });
            }

            if (viewers.size() > 0) {
                Collections.sort(viewers, Comparator.comparing(JLabel::getText));
            }

            for (JLabel label : viewers) {
                this.add(label);
                this.validate();
                this.repaint();
            }
        });
    }

    private String[] commands = new String[]{"", "Ban", "Un-Ban", "Timeout", "Un-Timeout"};

    private void onMouseClicked(MouseEvent e) {
        for (JLabel viewer : this.viewers) {
            if (e.getSource() == viewer) {

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
                        Launch.getInstance().getBot().getChannel().messageChannel("/ban " + viewer
                                .getText());
                        break;
                    case "Un-Ban":
                        Launch.getInstance().getBot().getChannel().messageChannel("/unban " + viewer
                                .getText());
                        break;
                    case "Timeout":
                        Launch.getInstance().getBot().getChannel().messageChannel("/timeout " + viewer
                                .getText());
                        break;
                    case "Un-Timeout":
                        Launch.getInstance().getBot().getChannel().messageChannel("/untimeout " + viewer
                                .getText());
                        break;
                }
            }
        }
    }
}
