package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class QuitRoomRequest extends Request {

    private int roomId;

    public QuitRoomRequest(int roomId) {
        super(Action.REQ_QUIT_ROOM);
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }

}
