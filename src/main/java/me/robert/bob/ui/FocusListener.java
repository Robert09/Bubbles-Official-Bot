package me.robert.bob.ui;

import javax.swing.*;
import java.awt.event.FocusEvent;

/**
 * Created by O3Bubbles09 on 2/21/2017
 **/
public class FocusListener implements java.awt.event.FocusListener {

    @Override
    public void focusGained(FocusEvent e) {
        JTextField textField = (JTextField) e.getComponent();
        if (textField.getText().equalsIgnoreCase("Enter a message to send."))
            textField.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField textField = (JTextField) e.getComponent();
        if (textField.getText().isEmpty())
            textField.setText("Enter a message to send.");
    }
}
