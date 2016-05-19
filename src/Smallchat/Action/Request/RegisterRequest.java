package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class RegisterRequest extends Request {

    private String username;
    private String password;

    public RegisterRequest(String username, String password) {
        super(Action.REQ_REGISTER);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
