package Smallchat.Server;

import Smallchat.Action.Action;
import Smallchat.Action.Response.SetRoomMemberResponse;
import Smallchat.Model.RoomMemberModel;
import Smallchat.Model.RoomModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Room {

    private int roomId;
    private boolean deletable;
    private String roomName;
    private HashMap<Integer, RoomMember> members;

    public Room(RoomModel model) {
        this.roomId = model.getRoomId();
        this.roomName = model.getRoomName();

        deletable = true;
        members = new HashMap<>();
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Collection<RoomMember> getMembers() {
        return members.values();
    }

    public HashMap<Integer, RoomMember> getMembersHashMap() {
        return members;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean value) {
        deletable = value;
        tryDelete();
    }

    private boolean tryDelete() {
        if (deletable && members.size() == 0) {
            RoomManager.getInstance().onRemove(roomId);
            return true;
        }
        return false;
    }

    public synchronized boolean addMember(User user) {
        int userId = user.getUserId();
        RoomMember member = members.get(userId);
        if (member != null) {
            if (member.isActive()) return false;
            members.remove(userId);
        }

        member = new RoomMember(this, user);
        member.onJoin();
        members.put(userId, member);

        SetRoomMemberResponse model = new SetRoomMemberResponse(
                roomId,
                new RoomMemberModel(member),
                SetRoomMemberResponse.JOIN
        );
        broadcast(model);

        return true;
    }

    public boolean removeMember(int userId) {
        RoomMember member = members.get(userId);
        if (member == null) {
            return false;
        }

        SetRoomMemberResponse model = new SetRoomMemberResponse(
                roomId,
                new RoomMemberModel(member),
                SetRoomMemberResponse.QUIT
        );
        broadcast(model);

        member.onLeave();

        return true;
    }

    public synchronized <T extends Action> void broadcast(T model) {
        Iterator<RoomMember> iter = members.values().iterator();
        while (iter.hasNext()) {
            RoomMember member = iter.next();
            if (!member.send(model)) {
                iter.remove();
            }
        }
        tryDelete();
    }

}
