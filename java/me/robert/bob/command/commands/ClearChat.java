package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;
import me.robert.bob.files.Configuration;

/**
 * Created by O3Bubbles09 on 2/7/2017
 **/
@CommandInfo(description = "Configure when chat is cleared.", usage = "!cc <delay> <delay in minutes>", needsMod = true, aliases = {"clearChat", "cc"})
public class ClearChat extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        if (args.length == 0) {
            Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(this.getClass().getAnnotation(CommandInfo.class).usage());
            return;
        }

        String cmd = args[0];
        if (cmd.equalsIgnoreCase("delay")) {
            if (args.length > 2) {
                Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(this.getClass().getAnnotation(CommandInfo.class).usage());
                return;
            }

            int delay;

            try {
                delay = Integer.parseInt(args[1]);
            } catch (Exception e) {
                Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel("Usage: !clearChat delay <time in minutes>");
                return;
            }

            Configuration.getConfiguration().set("clearChatDelay", delay);
            Launch.getInstance().getBot().getWindow().getChatTab().getChat(channel.substring(1)).getClearChat().start((Integer) Configuration.getConfiguration().get("clearChatDelay"));
            Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel("Successfully set the clear chat to delay to every: " + delay + " minutes");
            return;
        }
    }
}
