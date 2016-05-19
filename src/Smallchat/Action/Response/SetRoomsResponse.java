package Smallchat.Action.Response;

import Smallchat.Action.Action;
import Smallchat.Model.RoomModel;

import java.util.HashMap;
import java.util.Map;

public class SetRoomsResponse extends Response {

    private HashMap<Integer, RoomModel> rooms;

    public SetRoomsResponse(HashMap<Integer, RoomModel> rooms) {
        super(Action.RES_SET_ROOMS, 0);
        this.rooms = rooms;
    }

    public HashMap<Integer, RoomModel> getRooms() {
        return rooms;
    }

}