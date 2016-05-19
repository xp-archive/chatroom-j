package Smallchat.Action.Response;

import Smallchat.Action.Action;

public class LoginResponse extends Response {

    private int userId;
    private String username;

    public LoginResponse(int code, int userId, String username) {
        super(Action.RES_LOGIN, code);
        this.userId = userId;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
