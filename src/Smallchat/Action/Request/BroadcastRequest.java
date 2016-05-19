package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class BroadcastRequest extends Request {

    private int roomId;
    private String message;

    public BroadcastRequest(int roomId, String message) {
        super(Action.REQ_BROADCAST);
        this.roomId = roomId;
        this.message = message;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

}
