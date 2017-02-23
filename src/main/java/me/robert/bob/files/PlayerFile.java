package me.robert.bob.files;

import java.io.File;

/**
 * Created by O3Bubbles09 on 2/16/2017
 **/
public class PlayerFile extends SettingsFile {

    public PlayerFile(String playerName) {
        super("players", String.format("%s.yaml", playerName));
    }

    public PlayerFile(File file) {
        super(file);
    }
}
