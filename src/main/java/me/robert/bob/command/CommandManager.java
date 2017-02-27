package me.robert.bob.command;

import me.robert.bob.Launch;
import me.robert.bob.command.commands.AddCommand;
import me.robert.bob.command.commands.Points;
import me.robert.irc.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/16/2017
 **/
public class CommandManager {

    private List<Command> commands;
    private List<CustomCommand> customCommands;

    private String channel;

    public CommandManager(String channel) {
        this.channel = channel;
        this.init();
    }

    private void init() {
        this.commands = new ArrayList<>();

        commands.add(new AddCommand());
        commands.add(new Points());

        this.loadCustomCommands();
    }

    private void loadCustomCommands() {
        this.customCommands = new ArrayList<>();

        File dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "BubblesBot" + File.separator + channel + File.separator + "commands");
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                System.out.println("DIRS created");
            }
        }

        if (dir.listFiles() == null)
            return;

        for (File file : dir.listFiles()) this.customCommands.add(new CustomCommand(file));
    }

    public void addCustomCommand(CustomCommand command) {
        this.customCommands.add(command);
        this.loadCustomCommands();
    }

    public void removeCustomCommand(CustomCommand command) {
        this.customCommands.remove(command);
        this.loadCustomCommands();
    }

    public void onCommand(String sender, boolean isMod, String channel, String command, String... args) {
        User user = Launch.getInstance().getBot().getChannel().getUser(sender);

        System.out.println("Command called: " + command);

        for (Command cmd : commands) {
            CommandInfo commandInfo = cmd.getClass().getAnnotation(CommandInfo.class);

            for (String alias : commandInfo.aliases()) {
                if (command.equalsIgnoreCase(alias)) {
                    if (commandInfo.needsMod()) {
                        if (user.isMod()) {
                            cmd.onCommand(sender, channel, command, args);
                            return;
                        } else
                            return;
                    }
                    cmd.onCommand(sender, channel, command, args);
                    return;
                }
            }
        }

        for (CustomCommand cmd : customCommands) {
            if (command.equalsIgnoreCase(cmd.getName())) {
                if (cmd.needsMod()) {
                    if (isMod) {
                        cmd.onCommand(sender, isMod, channel, command, args);
                        return;
                    } else return;
                }
                cmd.onCommand(sender, isMod, channel, command, args);
                return;
            }
        }
    }
}
