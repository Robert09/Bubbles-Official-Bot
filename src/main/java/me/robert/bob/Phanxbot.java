package me.robert.bob;

import me.robert.ircb.Channel;
import me.robert.ircb.IRCConnection;

import java.awt.*;

/**
 * Created by O3Bubbles09 on 2/10/2017
 **/
public class Phanxbot extends IRCConnection {

    public Phanxbot(String host, String username, String oAuth, Launch launch) {
        super(host, username, oAuth, true);

        this.sendRawMessage("JOIN #phanxgames");
    }

    @Override
    public void onMessage(String sender, boolean isMod, Color color, String channel, String message) {
        super.onMessage(sender, isMod, color, channel, message);
        System.out.println(String.format("[%s]: %s", sender, message));

        if (sender.equalsIgnoreCase("o3bubbles09") || sender.equalsIgnoreCase("phanxbot")) {
            if (message.contains("You are present for a victory moment on the stream! Type [!victory] to share the spoils! Thanks for watching the stream.")) {
                new Channel(channel.substring(1), this.getOut()).messageChannel("!victory");
            }
        }
    }
}
