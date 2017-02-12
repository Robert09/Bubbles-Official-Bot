package me.robert.bob.command;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
public abstract class Command {

    public abstract void onCommand(String sender, String channel, String command, String... args);
}
