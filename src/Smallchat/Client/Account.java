package Smallchat.Client;

import Smallchat.Model.UserModel;

public class Account {

    private volatile static Account instance = null;

    public static Account getInstance() { return instance; }

    public static void initInstance(UserModel model) {
        instance = new Account(model);
    }

    private int userId;
    private String username;

    private Account(UserModel model) {
        this.userId = model.getUserId();
        this.username = model.getUsername();
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

}
