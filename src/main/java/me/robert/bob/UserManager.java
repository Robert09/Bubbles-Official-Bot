package me.robert.bob;

import me.robert.ircb.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by O3Bubbles09 on 2/11/2017
 **/
public class UserManager {

    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    public User addUser(String name, String channel) {
        User user = new User(name, channel);
        this.users.add(user);
        return user;
    }

    public void removeUser(String user) {
        this.users.remove(getUser(user));
    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name))
                return user;
        }
        return null;
    }
}
