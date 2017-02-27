package me.robert.bob;

import me.robert.bob.ui.Window;

/**
 * Created by O3Bubbles09 on 2/13/2017
 **/
public class Launch {

    private Bot bot;

    private Window window;

    public static final String USER = "bubblesofficialbot";

    private Launch() {
        this.window = new Window();
//        System.out.println(HttpUtils.getResponseFromHttp("http://tmi.twitch.tv/group/user/o3bubbles09/chatters"));
    }

    private static Launch instance;

    public static Launch getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Launch();
    }

    public Bot getBot() {
        return this.bot;
    }

    public Window getWindow() {
        return this.window;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
        this.bot.start();
    }
}
