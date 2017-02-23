package me.robert.bob.files;

import java.io.File;

/**
 * Created by O3Bubbles09 on 2/20/2017
 **/
public class CommandFile extends SettingsFile {

    public CommandFile(String name, boolean needsMod, int cost, String response) {
        super("commands", name + ".yaml");

        this.set("name", name);
        this.set("needsMod", needsMod);
        this.set("cost", cost);
        this.set("response", response);
    }

    public CommandFile(File file) {
        super(file);
    }
}
