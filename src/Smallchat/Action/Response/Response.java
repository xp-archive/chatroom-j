package Smallchat.Action.Response;

import Smallchat.Action.Action;

public abstract class Response extends Action {

    private int code;

    public Response(String action, int code) {
        super(action);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isZeroCode() {
        return code == 0;
    }
}
