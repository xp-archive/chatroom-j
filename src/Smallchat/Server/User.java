package Smallchat.Server;

import Smallchat.Action.*;
import Smallchat.Model.RoomModel;
import Smallchat.Model.UserModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class User {

    private Client client;

    private int userId;
    private String username;
    private HashMap<Integer, Room> rooms;

    public User(Client client, UserModel model) {
        this.client = client;
        this.userId = model.getUserId();
        this.username = model.getUsername();
        this.rooms = new HashMap<>();

        UserManager.getInstance().add(this);
    }

    public <T extends Action> boolean send(T model) {
        if (!isActive()) return false;

        System.out.println(String.format("send %d.%s()", getUserId(), model.getAction()));

        return client.send(model);
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public boolean isActive() {
        return client.isActive();
    }

    public void onJoinToRoom(Room room) {
        rooms.put(room.getRoomId(), room);
    }

    public void onLeaveFromRoom(int roomId) {
        rooms.remove(roomId);
    }

    public void exit() {
        // 主动退出
        client.close();
    }

    public void onExit() {
        System.out.println(String.format("%d(%s) offline", getUserId(), getUsername()));

        Iterator<Room> iter = rooms.values().iterator();
        while (iter.hasNext()) {
            Room room = iter.next();
            room.removeMember(getUserId());
            iter.remove();
        }

        UserManager.getInstance().onRemove(this);
    }

}
