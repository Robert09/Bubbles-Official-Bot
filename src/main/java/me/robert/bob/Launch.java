package me.robert.bob;

import me.robert.bob.files.Configuration;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class Launch {

    private BubblesOfficialBot bot;

    private Launch() {
        this.bot = new BubblesOfficialBot("irc.twitch.tv", (String) Configuration.getLogin().get("username"), (String) Configuration.getLogin().get("oAuth"), this);
        SpeechRecognition recognition = new SpeechRecognition();
        Thread speech = new Thread(recognition);
        speech.start();
        //        Phanxbot test = new Phanxbot("irc.twitch.tv", (String) Configuration.getLogin().get("username"), (String) Configuration.getLogin().get("oAuth"), this);
    }

    private static Launch instance;

    public static Launch getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Launch();
    }

    public BubblesOfficialBot getBot() {
        return this.bot;
    }
}
