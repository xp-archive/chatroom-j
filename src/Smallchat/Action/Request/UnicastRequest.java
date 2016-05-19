package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class UnicastRequest extends Request {

    private int userId;
    private String message;

    public UnicastRequest(int userId, String message) {
        super(Action.REQ_UNICAST);
        this.userId = userId;
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

}
