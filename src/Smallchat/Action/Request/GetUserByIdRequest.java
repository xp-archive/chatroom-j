package Smallchat.Action.Request;

import Smallchat.Action.Action;

public class GetUserByIdRequest extends Request {

    private int userId;

    public GetUserByIdRequest(int userId) {
        super(Action.REQ_GET_USER_BY_ID);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

}
