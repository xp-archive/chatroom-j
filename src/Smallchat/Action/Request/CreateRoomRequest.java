package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class CreateRoomRequest extends Request {

    private String roomName;

    public CreateRoomRequest(String roomName) {
        super(Action.REQ_CREATE_ROOM);
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

}
