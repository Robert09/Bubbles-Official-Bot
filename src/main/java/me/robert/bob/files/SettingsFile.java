package me.robert.bob.files;

import me.robert.bob.Launch;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by O3Bubbles09 on 2/16/2017
 **/
public class SettingsFile {

    private File file;
    private File dir;
    private DumperOptions dumperOptions;
    private Yaml yaml;

    private Map<String, Object> config;

    public SettingsFile(String fileName) {
        dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "BubblesBot" + File.separator + Launch.getInstance().getBot().getChannel().getName() + File.separator);
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
            config = new HashMap<>();
        }
    }

    SettingsFile(String folder, String fileName) {
        dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "BubblesBot" + File.separator + Launch.getInstance().getBot().getChannel().getName() + File.separator + folder + File.separator);
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
            config = new HashMap<>();
        }
    }

    SettingsFile(File file) {
        this.file = file;

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
            config = new HashMap<>();
        }
    }

    public File getFile() {
        return this.file;
    }

    boolean contains(String key) {
        return config.containsKey(key);
    }

    void save() {
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
        return contains(key) && (boolean) this.get(key);
    }

    public Integer getInt(String key) {
        if (contains(key))
            return (Integer) this.get(key);
        else return 0;
    }

    public String getString(String key) {
        return (String) this.get(key);
    }

    public void set(String key, Object value) {
        config.put(key, value);
        save();
    }


}
