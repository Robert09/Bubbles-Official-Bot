package me.robert.bob.files;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by O3Bubbles09 on 1/30/2017
 **/
public class Configuration {

    private File file;
    private File dir;
    private DumperOptions dumperOptions;
    private Yaml yaml;

    private Map<String, Object> config;

    private static Configuration configuration = new Configuration("config.yaml");

    public static Configuration getConfiguration() {
        return configuration;
    }

    private static Configuration login = new Configuration("login.yaml");

    public static Configuration getLogin() {
        return login;
    }

    private static Configuration notes = new Configuration("notes.yaml");

    public static Configuration getNotes() {
        return notes;
    }

    private Configuration(String fileName) {
        dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "Bubbles Official Bot" + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir.getAbsolutePath() + File.separator + fileName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace(); // TODO: Output to console.
            }
        }

        dumperOptions = new DumperOptions();
        dumperOptions.setPrettyFlow(true);
        yaml = new Yaml(dumperOptions);

        if (yaml != null) {
            try {
                config = (Map<String, Object>) yaml.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace(); // TODO: Output to console.
            }
        }

        if (config == null) {
            config = new HashMap<String, Object>();
        }
    }

    public File getFile() {
        return this.file;
    }

    public boolean contains(String key) {
        return config.containsKey(key);
    }

    public void save() {
        try {
            yaml.dump(config, new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getMap() {
        return this.config;
    }

    public Object get(String key) {
        return config.get(key);
    }

    public boolean getBoolean(String key) {
        if (contains(key))
            return (boolean) get(key);
        else return false;
    }

    public void set(String key, Object value) {
        config.put(key, value);
        save();
    }
}
