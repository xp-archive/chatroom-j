package Smallchat.Action.Response;

import Smallchat.Action.Action;

public class UnicastResponse extends Response {

    private int senderId;
    private String message;

    public UnicastResponse(int senderId, String message) {
        super(Action.RES_UNICAST, 0);

        this.senderId = senderId;
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

}
