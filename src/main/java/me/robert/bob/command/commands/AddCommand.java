package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;

/**
 * Created by O3Bubbles09 on 2/11/2017
 **/
@CommandInfo(description = "Add a custom command for your bot.", usage = "!addcom <commandName> <needsMod> <response>", aliases = {"ac", "addcom"})
public class AddCommand extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        if (args.length <= 2) {

            Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(info.usage());
            return;
        }

        String commandName = args[0];
        boolean needsMod = Boolean.parseBoolean(args[1]);
        String response = "";

        for (int i = 2; i < args.length; i++) {
            response += args[i] + " ";
        }

        Launch.getInstance().getBot().getCommandManager().addCommand(commandName, needsMod, response);
        Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel("Successfully add the command: " + commandName + " " + needsMod + " " + response);
    }
}
