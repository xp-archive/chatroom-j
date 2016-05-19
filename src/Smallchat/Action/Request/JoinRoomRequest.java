package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class JoinRoomRequest extends Request {

    private int roomId;

    public JoinRoomRequest(int roomId) {
        super(Action.REQ_JOIN_ROOM);
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

}
