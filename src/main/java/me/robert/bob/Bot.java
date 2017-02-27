package me.robert.bob;

import me.robert.bob.command.CommandManager;
import me.robert.bob.files.PlayerFile;
import me.robert.bob.files.PlayerFileManager;
import me.robert.bob.files.SettingsFile;
import me.robert.irc.Connection;

import java.awt.*;

/**
 * Created by O3Bubbles09 on 2/13/2017
 **/
public class Bot extends Connection {

    private BotTimer timer;

    private CommandManager commandManager;

    private SettingsFile settingsFile;

    public Bot(String channel) {
        super(channel);

        PlayerFileManager.getInstance().loadPlayerFiles(channel);

        commandManager = new CommandManager(channel);
        timer = new BotTimer(1, this);

        if (this.getChannel() == null) System.out.println("The channel is null!");

        this.getChannel();
    }

    @Override
    public void onMessage(String sender, boolean isSenderMod, Color color, String channel, String message) {
        super.onMessage(sender, isSenderMod, color, channel, message);

        this.getChannel().getUser(sender).setColor(color);
        this.getChannel().getUser(sender).setName(sender);

        Launch.getInstance().getWindow().getChatPage().appendChat(sender, color, message);

        if (getChannel().getName().equalsIgnoreCase("phanxgames")) {
            if (message.contains("You are present for a victory moment on the stream! Type [!victory] to share the spoils! Thanks for watching the stream."))
                getChannel().messageChannel("!victory");
            if (message.contains("Daily quests have reset! Hurray! Type [!quests]"))
                getChannel().messageChannel("!claim");
        }
    }

    @Override
    public void sendRawMessage(String message) {
        super.sendRawMessage(message);
        Launch.getInstance().getWindow().getConsole().log(">>> " + message);
    }

    @Override
    public void onCommand(String sender, boolean isMod, String channel, String command, String... args) {
        super.onCommand(sender, isMod, channel, command, args);
        if (!this.getChannel().getName().equalsIgnoreCase("phanxgames")) {
            commandManager.onCommand(sender, isMod, channel, command, args);
        }
    }

    @Override
    public void onJoin(String sender) {
        this.getChannel().addUser(sender);
        PlayerFileManager.getInstance().addPlayer(sender.toLowerCase());
        super.onJoin(sender);
    }

    @Override
    public void onPart(String sender) {
        this.getChannel().removeUser(sender);
        PlayerFileManager.getInstance().removePlayer(sender.toLowerCase());
        super.onPart(sender);
    }

    @Override
    public void onLine(String line) {
        super.onLine(line);
        Launch.getInstance().getWindow().getConsole().log("<<< " + line);
    }

    @Override
    public void onUserTimedOut(String sender, int duration, String reason) {
        super.onUserTimedOut(sender, duration, reason);

        PlayerFile playerFile = PlayerFileManager.getInstance().getPlayerFile(sender);

        playerFile.set("points", playerFile.getInt("points") - this.settingsFile.getInt("timeoutPointsToTake"));

        this.getChannel().messageChannel(String.format("Sorry %s but you just lost %s points! You now have %s",
                sender, this.settingsFile.getInt("timeoutPointsToTake"), playerFile.getInt("points")));
    }

    @Override
    public void onUsers() {
        super.onUsers();

        Launch.getInstance().getWindow().getChatPage().updateViewers(this.getChannel().getUsers());
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void setSettingsFile(SettingsFile settingsFile) {
        this.settingsFile = settingsFile;
        this.settingsFile.set("timeoutPointsToTake", 100);
    }

    public SettingsFile getSettingsFile() {
        return this.settingsFile;
    }
}
