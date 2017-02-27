package me.robert.bob.files;

import java.io.File;
import java.util.HashMap;

/**
 * Created by O3Bubbles09 on 2/16/2017
 **/
public class PlayerFileManager {

    private HashMap<String, PlayerFile> playerFileHashMap;

    private PlayerFileManager() {
        this.playerFileHashMap = new HashMap<>();
    }

    private static PlayerFileManager instance = new PlayerFileManager();

    public static PlayerFileManager getInstance() {
        return instance;
    }

    public PlayerFile getPlayerFile(String player) {
        return this.playerFileHashMap.get(player.toLowerCase());
    }

    public void loadPlayerFiles(String channel) {
        File dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator +
                "Application Data" + File.separator + "BubblesBot" + File.separator + channel + File.separator +
                "players");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.listFiles().length == 0)
            return;

        for (File file : dir.listFiles()) {
            this.playerFileHashMap.put(file.getName().replace(".yaml", ""), new PlayerFile(file));
        }
    }

    public PlayerFile addPlayer(String player) {
        if (!this.playerFileHashMap.containsKey(player)) {
            this.playerFileHashMap.put(player.toLowerCase(), new PlayerFile(player.toLowerCase()));
        }
        return this.playerFileHashMap.get(player.toLowerCase());
    }

    public void removePlayer(String player) {
        this.playerFileHashMap.remove(player.toLowerCase());
    }
}
