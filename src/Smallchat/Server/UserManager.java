package Smallchat.Server;

import java.util.HashMap;
import java.util.HashSet;

public class UserManager {

    private static UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    private HashMap<Integer, User> users;
    private HashMap<String, Integer> name2id;

    private UserManager() {
        this.users = new HashMap<>();
        this.name2id = new HashMap<>();
    }

    public synchronized void add(User user) {
        int userId = user.getUserId();
        if (users.containsKey(userId)) {
            User old = users.get(userId);
            old.exit();
        }
        users.put(userId, user);
        name2id.put(user.getUsername(), userId);
    }

    public synchronized boolean registerUsername(String oldName, String newName) {
        Integer userId = name2id.get(oldName);
        if (userId == null) {
            return false;
        }
        name2id.remove(oldName);
        name2id.put(newName, userId);
        return true;
    }

    public synchronized void onRemove(User user) {
        users.remove(user.getUserId());
        name2id.remove(user.getUsername());
    }

    public User find(int userId) {
        return users.get(userId);
    }

    public User find(String username) {
        Integer userId = name2id.get(username);
        if (userId == null) return null;
        return find(userId);
    }

}
