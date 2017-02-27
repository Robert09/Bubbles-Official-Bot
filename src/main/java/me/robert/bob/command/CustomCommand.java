package me.robert.bob.command;

import me.robert.bob.Launch;
import me.robert.bob.files.CommandFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.Map;

/**
 * Created by O3Bubbles09 on 2/20/2017
 **/
public class CustomCommand {

    private String name;
    private String response;

    private boolean needsMod;

    private int cost;

    private File file;
    private File dir;
    private DumperOptions dumperOptions;
    private Yaml yaml;

    private Map<String, Object> config;

    public CustomCommand(String name, boolean needsMod, int cost, String response) {
        this.name = name;
        this.response = response;

        this.needsMod = needsMod;

        this.cost = cost;

        new CommandFile(name, needsMod, cost, response);
    }

    CustomCommand(File file) {
        CommandFile temp = new CommandFile(file);

        this.name = temp.getString("name");
        this.response = temp.getString("response");

        this.needsMod = temp.getBoolean("needsMod");

        this.cost = temp.getInt("cost");
    }

    void onCommand(String sender, boolean isMod, String channel, String command, String... args) {
        Launch.getInstance().getBot().getChannel().messageChannel(this.response);
    }

    String getName() {
        return this.name;
    }

    public String getResponse() {
        return this.response;
    }

    boolean needsMod() {
        return this.needsMod;
    }

    public int getCost() {
        return this.cost;
    }
}
