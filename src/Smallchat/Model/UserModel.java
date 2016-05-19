package Smallchat.Model;

import java.io.Serializable;
import java.util.HashMap;

public class UserModel implements Serializable {

    protected int userId;
    protected String username;
    protected HashMap<Integer, RoomModel> rooms;

    public UserModel(int userId, String username) {
        this.userId = userId;
        this.username = username;
        this.rooms = new HashMap<>();
    }

    public UserModel(Smallchat.Server.User user) {
        this(user.getUserId(), user.getUsername());
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public HashMap<Integer, RoomModel> getRooms() {
        return rooms;
    }

}
