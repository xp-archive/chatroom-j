package Smallchat.Client;

public class RoomMember {

    private int userId;
    private String username;
    private int status;

    public RoomMember(int userId, String username, int status) {
        this.userId = userId;
        this.username = username;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getStatus() {
        return status;
    }

}
