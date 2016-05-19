package Smallchat.Action;

import java.io.Serializable;

public abstract class Action implements Serializable {

    public static final String REQ_LOGIN = "login";
    public static final String RES_LOGIN = "login";

    public static final String REQ_REGISTER = "register";
    public static final String RES_REGISTER = "register";

    public static final String REQ_GET_ROOMS = "getRooms";
    public static final String RES_SET_ROOMS = "setRooms";

    public static final String REQ_CREATE_ROOM = "createRoom";
    public static final String REQ_JOIN_ROOM = "joinRoom";
    public static final String REQ_QUIT_ROOM = "quitRoom";
    public static final String REQ_GET_ROOM = "getRoom";
    public static final String RES_SET_ROOM = "setRoom";
    public static final String RES_SET_ROOM_MEMBER = "setRoomMember";

    public static final String REQ_BROADCAST = "broadcast";
    public static final String RES_BROADCAST = "broadcast";

    public static final String REQ_GET_USER_BY_NAME = "getUserByName";
    public static final String REQ_GET_USER_BY_ID = "getUserById";
    public static final String RES_SET_USER = "setUser";

    public static final String REQ_UNICAST = "unicast";
    public static final String RES_UNICAST = "unicast";


    protected String action;

    public Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

}
