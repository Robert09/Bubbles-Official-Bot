package me.robert.bob.command;

import me.robert.bob.Launch;
import me.robert.bob.files.CommandFile;

import java.io.File;

/**
 * Created by O3Bubbles09 on 2/11/2017
 **/
public class CustomCommand {

    private String name;
    private String response;
    private boolean needsMod;

    public CommandFile commandFile;

    public CustomCommand(String name, String response, boolean needsMod) {
        this.name = name;
        this.response = response;
        this.needsMod = needsMod;

        this.commandFile = new CommandFile(name + ".yaml");
        commandFile.set("commandName", name);
        commandFile.set("response", response);
        commandFile.set("needsMod", needsMod);
    }

    public CustomCommand(String name, String response) {
        this(name, response, false);
    }

    public CustomCommand(File file) {
        commandFile = new CommandFile(file);
        this.name = (String) commandFile.get("commandName");
        this.response = (String) commandFile.get("response");
        this.needsMod = (boolean) commandFile.get("needsMod");
    }

    public void onCommand(String sender, String channel, String command, String... args) {
        Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(response);
    }

    public String getName() {
        return this.name;
    }

    public String getResponse() {
        return this.response;
    }

    public boolean needsMod() {
        return this.needsMod;
    }
}
