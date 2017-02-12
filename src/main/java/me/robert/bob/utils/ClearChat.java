package me.robert.bob.utils;

import me.robert.ircb.Channel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by O3Bubbles09 on 2/6/2017
 **/
public class ClearChat extends TimerTask {

    private Channel channel;
    private Timer timer;

    private ClearWarning clearWarning;

    private boolean hasStarted = false;

    public ClearChat(Channel channel) {
        this.channel = channel;
        this.timer = new Timer();

        clearWarning = new ClearWarning(channel);
    }

    public boolean hasStarted() {
        return this.hasStarted;
    }

    public void start(int delay) {
        this.hasStarted = true;
        this.timer.cancel();

        this.timer = new Timer();
        timer.scheduleAtFixedRate(new ClearChat(channel), (long) (delay * 1000 * 60), (long) (delay * 1000 * 60));
        this.clearWarning.start(delay - 1);
    }

    public void run() {
        channel.messageChannel("/clear");
    }

    class ClearWarning extends TimerTask {

        private Timer timer;
        private Channel channel;

        public ClearWarning(Channel channel) {
            this.channel = channel;
            timer = new Timer();
        }

        public void start(int delay) {
            this.timer.cancel();

            this.timer = new Timer();
            timer.scheduleAtFixedRate(new ClearWarning(channel), (long) (delay * 1000 * 60), (long) (delay * 1000 * 60));
        }

        @Override
        public void run() {
            channel.messageChannel("--- Warning chat has been inactive and will clear in 1 minute ---");
        }
    }
}
