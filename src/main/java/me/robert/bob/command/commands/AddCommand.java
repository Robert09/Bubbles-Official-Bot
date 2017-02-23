package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;
import me.robert.bob.command.CustomCommand;
import me.robert.bob.files.PlayerFile;
import me.robert.bob.files.PlayerFileManager;
import me.robert.irc.User;

/**
 * Created by O3Bubbles09 on 2/7/2017
 **/
@CommandInfo(description = "Add a custom command for the bot.", usage = "!addCom", needsMod = true, aliases = {"addCom", "ac"})
public class AddCommand extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        User user = Launch.getInstance().getBot().getChannel().getUser(sender);

        if (!user.isMod()) {
            return;
        }

        if (args.length <= 4) {
            Launch.getInstance().getBot().getChannel().messageChannel("Usage: !addCom <name> <needsMod> <cost> <response>");
            return;
        }

        String cmd = args[1];

        boolean needsMod = Boolean.parseBoolean(args[2]);

        int cost = Integer.parseInt(args[3]);

        String response = "";

        for (int i = 4; i < args.length; i++) {
            response += " " + args[i];
        }

        Launch.getInstance().getBot().getCommandManager().addCustomCommand(new CustomCommand(cmd, needsMod, cost, response.substring(1)));
        Launch.getInstance().getBot().getChannel().messageChannel("You add the command: " + cmd + " to the bot.");
    }
}
