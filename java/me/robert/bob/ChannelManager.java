package me.robert.bob;

import me.robert.ircb.Channel;
import me.robert.ircb.IRCConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O3Bubbles09 on 1/29/2017
 **/
public class ChannelManager {

    private List<Channel> channelList;
    private IRCConnection connection;

    public ChannelManager(IRCConnection connection) {
        this.connection = connection;
        this.channelList = new ArrayList<>();
    }

    public Channel addChannel(String channel) {
        this.addChannel(new Channel(channel, connection.getOut()));
        return getChannel(channel);
    }

    public Channel addChannel(Channel channel) {
        this.channelList.add(channel);
        return channel;
    }

    public Channel getChannel(String channel) {
        for (Channel channel1 : this.channelList) {
            if (channel1.getName().equalsIgnoreCase(channel))
                return channel1;
        }
        return null;
    }

    public void removeChannel(String channel) {
        removeChannel(getChannel(channel));
    }

    public void removeChannel(Channel channel) {
        channel.part();
        channelList.remove(channel);
    }
}
