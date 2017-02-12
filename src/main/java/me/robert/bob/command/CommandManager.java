package me.robert.bob.command;

import me.robert.bob.Launch;
import me.robert.bob.command.commands.AddCommand;
import me.robert.bob.command.commands.ClearChat;
import me.robert.bob.command.commands.TakeNote;
import me.robert.bob.command.commands.Uptime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
public class CommandManager {

    private List<Command> commands;
    private List<CustomCommand> customCommands;

    public CommandManager() {
        this.init();
    }

    private void init() {
        this.commands = new ArrayList<>();
        this.customCommands = new ArrayList<>();

        // TODO: Add commands below here.
        commands.add(new AddCommand());
        commands.add(new ClearChat());
        commands.add(new TakeNote());
        commands.add(new Uptime());

        loadCommands();
    }

    public void loadCommands() {
        this.customCommands = new ArrayList<>();
        File commandFolder = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "Bubbles Official Bot" + File.separator + "custom commands" + File.separator);
        if (!commandFolder.exists())
            commandFolder.mkdirs();
        for (File file : commandFolder.listFiles()) {
            addCommand(file);
        }
    }

    public CustomCommand addCommand(File file) {
        CustomCommand temp = new CustomCommand(file);
        this.customCommands.add(temp);
        this.loadCommands();
        return temp;
    }

    public CustomCommand addCommand(String commandName, boolean needsMod, String response) {
        CustomCommand temp = new CustomCommand(commandName, response, needsMod);
        this.customCommands.add(temp);
        this.loadCommands();
        return temp;
    }

    public void onCommand(String sender, boolean isMod, String channel, String command, String... args) {
        if (command.equalsIgnoreCase("requestLink")) {
            Launch.getInstance().getBot().user = sender;
            return;
        }
        for (Command cmd : commands) {
            CommandInfo commandInfo = cmd.getClass().getAnnotation(CommandInfo.class);

            for (String alias : commandInfo.aliases()) {
                if (command.equalsIgnoreCase(alias)) {
                    if (commandInfo.needsMod()) {
                        if (isMod) {
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
                        cmd.onCommand(sender, channel, command, args);
                        return;
                    } else return;
                }

                cmd.onCommand(sender, channel, command, args);
                return;
            }
        }
    }
}
