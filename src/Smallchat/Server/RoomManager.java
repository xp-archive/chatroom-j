package Smallchat.Server;

import Smallchat.Model.RoomModel;

import java.util.HashMap;

public class RoomManager {

    private static RoomManager instance = new RoomManager();

    public static RoomManager getInstance() {
        return instance;
    }

    private int maxId;
    private HashMap<Integer, Room> rooms;

    private RoomManager() {
        maxId = 0;
        rooms = new HashMap<>();
    }

    public synchronized int allocRoomId() {
        return ++maxId;
    }

    public synchronized Room add(String roomName, User user) {
        int roomId = allocRoomId();
        Room room = new Room(new RoomModel(roomId, roomName));
        room.addMember(user);
        rooms.put(roomId, room);
        return room;
    }

    public synchronized void onRemove(int roomId) {
        rooms.remove(roomId);
    }

    public Room find(int roomId) {
        return rooms.get(roomId);
    }

}