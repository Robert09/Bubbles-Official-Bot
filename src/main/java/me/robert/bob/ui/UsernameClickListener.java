package me.robert.bob.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
public class UsernameClickListener extends AbstractAction {

    private String username;

    public UsernameClickListener(String username) {
        this.username = username;
    }

    protected void execute() {

    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        execute();
    }
}
