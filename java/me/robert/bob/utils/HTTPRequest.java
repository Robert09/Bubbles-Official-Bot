package me.robert.bob.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
public class HTTPRequest {

    public static HashMap<String, String> uptimes = new HashMap<>();

    public static String GetResponsefrom(String link) {
        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(new URL(link).openStream()));

            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);
        } catch (Exception e) {
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
            }
        }
        return buffer.toString();
    }

    public static void startUptime(String channel) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "";
                URL url = null;
                try {
                    url = new URL("https://api.twitch.tv/kraken/streams/" + channel + "?client_id=" + ClientID.CLIENT_ID);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    reader.close();

                    int create_index = result.indexOf("\"created_at\":") + 14;
                    String uptime = result.substring(create_index);

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    format.setTimeZone(TimeZone.getTimeZone("GMT"));
                    Date parse = format.parse(uptime);

                    long diff = System.currentTimeMillis() - parse.getTime();

                    int hours = (int) (diff / 3600000);
                    int remainder = (int) (diff - hours * 3600000);
                    int mins = (remainder / 60000);
                    remainder = (remainder - mins * 60000);
                    int secs = remainder / 1000;

                    uptimes.put(channel, String.format("%s:%s:%s", hours, mins, secs));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
