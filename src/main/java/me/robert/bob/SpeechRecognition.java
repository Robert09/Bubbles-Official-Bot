package me.robert.bob;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

import java.io.*;

/**
 * Created by O3Bubbles09 on 2/12/2017
 **/
public class SpeechRecognition implements Runnable {

    private File dir;
    private File dic;
    private File lm;

    public SpeechRecognition() {
        dir = new File(System.getProperty("user.home") + File.separator + "Local Settings" + File.separator + "Application Data" + File.separator + "Bubbles Official Bot" + File.separator + "res" + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        dic = new File("./res/bot.dic");
        lm = new File("./res/bot.lm");

        saveFile("bot.dic", new File(dir + File.separator + "bot.dic"));
        saveFile("bot.lm", new File(dir + File.separator + "bot.lm"));
    }

    private void saveFile(String file, File bfile) {
        if (!bfile.exists()) {
            try {
                bfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }

        InputStream inStream = getClass().getResourceAsStream(file);
        OutputStream outStream = null;

        try {
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            //delete the original file
            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath(String.format("file:%s%s%s", dir.getAbsolutePath(), File.separator, "bot.dic"));
        configuration.setLanguageModelPath(String.format("file:%s%s%s", dir.getAbsolutePath(), File.separator, "bot.lm"));
        configuration.setUseGrammar(false);

        LiveSpeechRecognizer recognizer = null;
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recognizer.startRecognition(true);

        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
//            for (WordResult word : result.getWords()) {
            System.out.println(result.getHypothesis());
            if (result.getHypothesis().equalsIgnoreCase("clear chat")) {
                Launch.getInstance().getBot().getChannelManager().getChannel("recanem").messageChannel("/clear");
            }

            if (result.getHypothesis().equalsIgnoreCase("allow")) {
                if (Launch.getInstance().getBot().user.length() > 0) {
                    Launch.getInstance().getBot().getChannelManager().getChannel("recanem").messageChannel("!allow " + Launch.getInstance().getBot().user);
                    Launch.getInstance().getBot().user = "";
                }
            }
//            }
        }
        recognizer.stopRecognition();
    }
}
