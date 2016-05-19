package Smallchat.Action.Response;

import Smallchat.Action.Action;
import Smallchat.Model.RoomModel;
import Smallchat.Model.UserModel;

public class BroadcastResponse extends Response {

    private int senderId;
    private int roomId;
    private String message;

    public BroadcastResponse(int senderId, int roomId, String message) {
        super(Action.RES_BROADCAST, 0);
        this.senderId = senderId;
        this.roomId = roomId;
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

}
