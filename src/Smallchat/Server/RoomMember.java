package Smallchat.Server;

import Smallchat.Action.Action;
import Smallchat.Action.Response.SetRoomMemberResponse;
import Smallchat.Model.RoomMemberModel;

public class RoomMember {

    private Room room;
    private User user;

    public RoomMember(Room room, User user) {
        this.room = room;
        this.user = user;
    }

    public <T extends Action> boolean send(T model) {
        return user != null && user.send(model);
    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return user != null && user.isActive();
    }

    public void onJoin() {
        user.onJoinToRoom(room);
    }

    public void onLeave() {
        if (!user.isActive()) return ;
        user.onLeaveFromRoom(room.getRoomId());
        user = null;
    }

}
