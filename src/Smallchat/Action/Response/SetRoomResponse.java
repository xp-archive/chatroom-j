package Smallchat.Action.Response;

import Smallchat.Action.Action;
import Smallchat.Model.RoomMemberModel;
import Smallchat.Model.RoomModel;

import java.util.HashMap;

public class SetRoomResponse extends Response {

    public static final int CREATE = 0;
    public static final int NORMAL = 1;
    public static final int DISMISS = 2;
    public static final int CHANGE = 3;
    public static final int JOIN = 4;

    private int status;
    private RoomModel room;

    public SetRoomResponse(int status, RoomModel room) {
        super(Action.RES_SET_ROOM, 0);
        this.status = status;
        this.room = room;
    }

    public int getStatus() {
        return status;
    }

    public RoomModel getRoom() {
        return room;
    }

}
