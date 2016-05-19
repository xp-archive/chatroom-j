package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class GetRoomRequest extends Request {

    private int roomId;

    public GetRoomRequest(int roomId) {
        super(Action.REQ_GET_ROOM);
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

}
