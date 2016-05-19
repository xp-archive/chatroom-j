package Smallchat.Action.Response;

import Smallchat.Action.Action;

public class RegisterResponse extends Response {

    private int userId;
    private String username;

    public RegisterResponse(int code, int userId, String username) {
        super(Action.RES_REGISTER, code);
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
