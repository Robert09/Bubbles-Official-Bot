package me.robert.bob.command;

import me.robert.bob.command.commands.ClearChat;
import me.robert.bob.command.commands.TakeNote;
import me.robert.bob.command.commands.Uptime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
public class CommandManager {

    private List<Command> commands;

    public CommandManager() {
        this.init();
    }

    private void init() {
        this.commands = new ArrayList<>();

        // TODO: Add commands below here.
        commands.add(new ClearChat());
        commands.add(new TakeNote());
        commands.add(new Uptime());
    }

    public void onCommand(String sender, String channel, String command, String... args) {
        System.out.println("...Command Manager...");
        System.out.println("Command: " + command);
        for (Command cmd : commands) {
            CommandInfo commandInfo = cmd.getClass().getAnnotation(CommandInfo.class);

            for (String alias : commandInfo.aliases()) {
                System.out.println("Alias: " + alias);
                if (command.equalsIgnoreCase(alias)) {
                    cmd.onCommand(sender, channel, command, args);
                }
            }
        }
    }
}
