package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;
import me.robert.bob.utils.HTTPRequest;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
@CommandInfo(description = "Let's you know how long the stream has been going.", usage = "", needsMod = false, aliases = {"uptime", "ut"})
public class Uptime extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(channel.substring(1) + " has been live for " + HTTPRequest.uptimes.get(channel.substring(1)));
    }
}
