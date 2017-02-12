package me.robert.bob.command.commands;

import me.robert.bob.Launch;
import me.robert.bob.command.Command;
import me.robert.bob.command.CommandInfo;
import me.robert.bob.files.Configuration;
import me.robert.bob.utils.HTTPRequest;

/**
 * Created by O3Bubbles09 on 2/4/2017
 **/
@CommandInfo(description = "Take a note for later.", usage = "!tn <note>", needsMod = true, aliases = {"test", "tn"})
public class TakeNote extends Command {

    @Override
    public void onCommand(String sender, String channel, String command, String... args) {
        if (args.length == 0 && Configuration.getNotes().getMap().isEmpty()) {
            Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(this.getClass().getAnnotation(CommandInfo.class).usage());
            return;
        } else if (!Configuration.getNotes().getMap().isEmpty()) {
            for (String string : Configuration.getNotes().getMap().keySet()) {
                String noteNumber = string;
                String createdAt = (String) Configuration.getNotes().get(noteNumber + ".time");
                String note = (String) Configuration.getNotes().get(noteNumber + ".note");

                Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel(String.format("Here is your note created at: %s, %s", createdAt, note));
            }
        }

        String note = "";
        for (String s : args) {
            note += s + " ";
        }

        int noteCount = 0;

        if (Configuration.getNotes().contains("noteCount"))
            noteCount = (int) Configuration.getNotes().get("noteCount");

        String time = HTTPRequest.uptimes.get(channel.substring(1));

        Configuration.getNotes().set(noteCount + ".time", time);
        Configuration.getNotes().set(noteCount + ".note", note);

        Launch.getInstance().getBot().getChannelManager().getChannel(channel.substring(1)).messageChannel("You have succefully created a note " + time + " into the stream.");
    }
}
