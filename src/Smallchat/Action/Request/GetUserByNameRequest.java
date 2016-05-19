package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class GetUserByNameRequest extends Request {

    private String username;

    public GetUserByNameRequest(String username) {
        super(Action.REQ_GET_USER_BY_NAME);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
