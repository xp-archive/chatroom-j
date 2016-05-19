package Smallchat.Model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RoomMemberModel extends UserModel {

    public static final int ACTIVE = 0;
    public static final int INACTIVE = 1;

    protected int roomId;
    protected int status;

    public RoomMemberModel(int roomId, int status, int userId, String username) {
        super(userId, username);
        this.roomId = roomId;
        this.status = status;
    }

    public RoomMemberModel(Smallchat.Server.RoomMember member) {
        this(
                member.getRoom().getRoomId(),
                member.getUser().isActive() ? ACTIVE : INACTIVE,
                member.getUser().getUserId(),
                member.getUser().getUsername()
        );
    }

    public static HashMap<Integer, RoomMemberModel> toMap(Collection<Smallchat.Server.RoomMember> members) {
        HashMap<Integer, RoomMemberModel> models = new HashMap<>();
        Iterator<Smallchat.Server.RoomMember> iter = members.iterator();
        while (iter.hasNext()) {
            Smallchat.Server.RoomMember member = iter.next();
            models.put(member.getUser().getUserId(), new RoomMemberModel(member));
        }
        return models;
    }

    public int getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return status == ACTIVE;
    }

    public int getStatus() {
        return status;
    }
}
