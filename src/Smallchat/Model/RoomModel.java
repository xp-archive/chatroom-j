package Smallchat.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class RoomModel implements Serializable {

    protected int roomId;
    protected String roomName;
    protected HashMap<Integer, RoomMemberModel> members = null;

    public RoomModel(int roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public RoomModel(Smallchat.Server.Room room) {
        this(room.getRoomId(), room.getRoomName());
        this.members = RoomMemberModel.toMap(room.getMembers());
    }

    public static HashMap<Integer, RoomModel> toMap(Collection<Smallchat.Server.Room> rooms) {
        HashMap<Integer, RoomModel> models = new HashMap<>();
        Iterator<Smallchat.Server.Room> iter = rooms.iterator();
        while (iter.hasNext()) {
            Smallchat.Server.Room room = iter.next();
            models.put(room.getRoomId(), new RoomModel(room));
        }
        return models;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public Collection<RoomMemberModel> getMembers() {
        return members.values();
    }

}
