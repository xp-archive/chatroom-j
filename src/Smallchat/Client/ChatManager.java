package Smallchat.Client;

import java.util.HashMap;

public class ChatManager {

    private static ChatManager instance = new ChatManager();

    public static ChatManager getInstance() { return instance; }

    private HashMap<Integer, Chat> chats;
    private HashMap<Integer, Room> rooms;

    private ChatManager() {
        chats = new HashMap<>();
        rooms = new HashMap<>();
    }

    public Chat getChat(int userId) {
        Chat chat = chats.get(userId);
        if (chat == null) {
            chat = new Chat();
            chat.setUserId(userId);
            chats.put(userId, chat);
        }
        return chat;
    }

    public void removeChat(int userId) {
        chats.remove(userId);
    }

    public Room getRoom(int roomId) {
        Room room = rooms.get(roomId);
        if (room == null) {
            room = new Room();
            room.setRoomId(roomId);
            rooms.put(roomId, room);
        }
        return room;
    }

    public void removeRoom(int roomId) {
        rooms.remove(roomId);
    }

}
