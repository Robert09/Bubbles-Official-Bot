package me.robert.bob;

import me.robert.bob.command.CommandManager;
import me.robert.bob.files.Configuration;
import me.robert.bob.ui.Window;
import me.robert.bob.ui.tabs.TabChat;
import me.robert.ircb.IRCConnection;

import java.awt.*;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class BubblesOfficialBot extends IRCConnection {

    private String username;

    private ChannelManager channelManager;
    private CommandManager commandManager;

    private Window window;

    private Launch launch;

    public BubblesOfficialBot(String host, String username, String oAuth, Launch launch) {
        super(host, username, oAuth, true);
        this.launch = launch;
        this.username = username;

        this.channelManager = new ChannelManager(this);
        this.commandManager = new CommandManager();

        this.window = new Window(this);
    }

    @Override
    public void onMessage(String sender, Color color, String channel, String message) {
        super.onMessage(sender, color, channel, message);

        if (sender.equalsIgnoreCase("o3bubbles09") || sender.equalsIgnoreCase("phanxbot")) {
            if (message.contains("You are present for a victory moment on the stream! Type [!victory] to share the spoils! Thanks for watching the stream.")) {
                this.getChannel(channel.substring(1)).messageChannel("!victory");
            }
        }

        System.out.printf("Sender: %s, Color: %s, Channel: %s, Message: %s", sender, color, channel, message);

        if (color != Color.BLACK)
            getWindow().getChatTab().getChat(channel.substring(1)).appendChat(sender, color, message);
        else
            getWindow().getChatTab().getChat(channel.substring(1)).appendChat(sender, Color.RED, message);

        if (window.getChatTab().getChat(channel.substring(1)).getClearChat().hasStarted()) {
            window.getChatTab().getChat(channel.substring(1)).getClearChat().start((Integer) Configuration.getConfiguration().get("clearChatDelay"));
        }
    }

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        super.onCommand(sender, channel, command, args);
        this.commandManager.onCommand(sender, channel, command, args);
    }

    public ChannelManager getChannelManager() {
        return this.channelManager;
    }


    public Window getWindow() {
        return this.window;
    }

}
