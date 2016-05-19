package Smallchat.Action.Response;

import Smallchat.Action.Action;
import Smallchat.Model.UserModel;

public class SetUserResponse extends Response {

    private UserModel user;

    public SetUserResponse(int code) {
        super(Action.RES_SET_USER, code);
    }

    public void setUser(int userId, String username) {
        this.user = new UserModel(userId, username);
    }

    public void setUser(int userId) {
        this.user = new UserModel(userId, null);
    }

    public void setUser(String username) {
        this.user = new UserModel(0, username);
    }

    public int getUserId() { return user.getUserId(); }

    public String getUsername() { return user.getUsername(); }

}
