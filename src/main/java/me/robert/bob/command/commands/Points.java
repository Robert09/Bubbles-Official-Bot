package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;
import me.robert.bob.files.PlayerFile;
import me.robert.bob.files.PlayerFileManager;
import me.robert.irc.User;

/**
 * Created by O3Bubbles09 on 2/7/2017
 **/
@CommandInfo(description = "Check how many points you have.", usage = "!points", needsMod = false, aliases = {"points"})
public class Points extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        User user = Launch.getInstance().getBot().getChannel().getUser(sender);

        if (args.length == 1) {
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("%s your current points are: %s", sender, PlayerFileManager.getInstance().getPlayerFile(sender).get("points")));
            return;
        }

        if (args.length == 2) {
            String username = args[1];
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("%s's current points are: %s",
                    username, PlayerFileManager.getInstance().getPlayerFile(username).getInt("points")));
            return;
        }

        if (!user.isMod()) {
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("%s your current points are: %s", sender, PlayerFileManager.getInstance().getPlayerFile(sender).get("points")));
            return;
        }

        if (args.length <= 3) {
            Launch.getInstance().getBot().getChannel().messageChannel("Usage: !points <add | remove> <user> <amount>");
            return;
        }

        String cmd = args[1];
        String username = args[2];
        int amount = Integer.parseInt(args[3]);

        PlayerFile playerFile = PlayerFileManager.getInstance().getPlayerFile(username);

        if (cmd.equalsIgnoreCase("add")) {
            playerFile.set("points", playerFile.getInt("points") + amount);
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("Successfully added %s to %s", amount, username));
            return;
        }

        if (cmd.equalsIgnoreCase("remove")) {
            playerFile.set("points", playerFile.getInt("points") - amount);
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("Successfully added %s to %s", amount, username));
            return;
        }

        if (cmd.equalsIgnoreCase("set")) {
            playerFile.set("points", amount);
            Launch.getInstance().getBot().getChannel().messageChannel(String.format("Successfully set %s's points to " +
                            "%s",
                    username, amount));
            return;
        }
    }
}
