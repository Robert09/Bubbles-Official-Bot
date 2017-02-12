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
    private UserManager userManager;

    private Window window;

    private Launch launch;

    public BubblesOfficialBot(String host, String username, String oAuth, Launch launch) {
        super(host, username, oAuth, true);
        this.launch = launch;
        this.username = username;

        this.channelManager = new ChannelManager(this);
        this.commandManager = new CommandManager();
        this.userManager = new UserManager();

        this.window = new Window(this);
    }

    public static String user = "";

    @Override
    public void onMessage(String sender, boolean isMod, Color color, String channel, String message) {
        super.onMessage(sender, isMod, color, channel, message);

        if (isMod)
            this.userManager.getUser(sender).setMod(isMod);

        if (color != Color.BLACK)
            getWindow().getChatTab().getChat(channel.substring(1)).appendChat(sender, color, message);
        else
            getWindow().getChatTab().getChat(channel.substring(1)).appendChat(sender, Color.RED, message);

        if (window.getChatTab().getChat(channel.substring(1)).getClearChat().hasStarted()) {
            window.getChatTab().getChat(channel.substring(1)).getClearChat().start((Integer) Configuration.getConfiguration().get("clearChatDelay"));
        }
    }

    @Override
    public void onCommand(String sender, boolean isMod, String channel, String command, String... args) {
        super.onCommand(sender, isMod, channel, command, args);
        this.commandManager.onCommand(sender, isMod, channel, command, args);
    }

    @Override
    public void onJoin(String sender, String channel) {
        super.onJoin(sender, channel);
        this.userManager.addUser(sender, channel);
    }

    @Override
    public void onPart(String sender, String channel) {
        super.onPart(sender, channel);
        this.userManager.removeUser(sender);
    }

    public ChannelManager getChannelManager() {
        return this.channelManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
    }

    public Window getWindow() {
        return this.window;
    }

}
