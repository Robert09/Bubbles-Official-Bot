package me.robert.bob;

import me.robert.bob.files.PlayerFileManager;
import me.robert.irc.User;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by O3Bubbles09 on 2/16/2017
 **/
public class BotTimer extends TimerTask {

    private Timer timer;

    public BotTimer(int delay, Bot bot) {
        int timeTime = delay * 60 * 1000;

        this.timer = new Timer("Bot Timer");
        this.timer.scheduleAtFixedRate(this, timeTime, timeTime);
    }

    @Override
    public void run() {
        Launch.getInstance().getBot().onUsers();
        if (Launch.getInstance().getBot().getChannel() == null)
            return;
        for (User user : Launch.getInstance().getBot().getChannel().getUsers()) {
            if (user.isTimedOut())
                PlayerFileManager.getInstance().getPlayerFile(user.getName()).set("points", PlayerFileManager.getInstance().getPlayerFile(user.getName()).getInt("points") + 1);
            else
                PlayerFileManager.getInstance().getPlayerFile(user.getName()).set("points", PlayerFileManager.getInstance().getPlayerFile(user.getName()).getInt("points") + 2);
        }
    }
}
