package Smallchat.Action.Response;

import Smallchat.Action.Action;
import Smallchat.Model.RoomMemberModel;

public class SetRoomMemberResponse extends Response {

    public static final int JOIN = 0;
    public static final int QUIT = 1;

    private int status;
    private int roomId;
    private RoomMemberModel member;

    public SetRoomMemberResponse(int roomId, RoomMemberModel member, int status) {
        super(Action.RES_SET_ROOM_MEMBER, 0);
        this.roomId = roomId;
        this.member = member;
        this.status = status;
    }

    public int getRoomId() {
        return roomId;
    }

    public RoomMemberModel getMember() {
        return member;
    }

    public int getUserId() {
        return member.getUserId();
    }

    public String getUsername() {
        return member.getUsername();
    }

    public int getStatus() {
        return status;
    }
}
