package Smallchat.Client;

import Smallchat.Action.Action;
import Smallchat.Action.Request.GetUserByIdRequest;
import Smallchat.Action.Response.*;
import Smallchat.Model.RoomModel;
import Smallchat.Model.UserModel;
import Smallchat.Server.ICallback;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResponseHandler {

    private Client client;

    public ResponseHandler(Client client) {
        this.client = client;
        client.setOnReceive(new ICallback() {
            @Override
            public int execute(Object... args) {
                Action action = (Action)args[0];
                try {
                    System.out.println(String.format("receive %s()", action.getAction()));
                    Method myMethod = ResponseHandler.class.getMethod(action.getAction(), Action.class);
                    myMethod.invoke(ResponseHandler.this, action);
                    return 0;
                } catch (NoSuchMethodException ex) {
                    System.out.println(String.format("%s() not found", action.getAction()));
                    return -1;
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                    return -1;
                }
            }
        });
    }

    private void setAccount(UserModel model) {
        Account.initInstance(model);
        FrmMain.getInstance().setVisible(true);
    }

    public void register(Action action) {
        RegisterResponse response = (RegisterResponse) action;
        if (response.isZeroCode()) {
            FrmRegister.getInstance().dispose();
            setAccount(new UserModel(response.getUserId(), response.getUsername()));
        } else {
            FrmRegister.getInstance().onRegisterFailure(response.getUsername());
        }
    }

    public void login(Action action) {
        LoginResponse response = (LoginResponse) action;
        if (response.isZeroCode()) {
            FrmLogin.getInstance().dispose();
            setAccount(new UserModel(response.getUserId(), response.getUsername()));
        } else {
            FrmLogin.getInstance().onLoginFailure(response.getUsername());
        }
    }

    public void setRooms(Action action) {
        SetRoomsResponse response = (SetRoomsResponse)action;
        //TODO
    }

    public void setRoom(Action action) {
        SetRoomResponse response = (SetRoomResponse)action;
        RoomModel model = response.getRoom();
        Room room = ChatManager.getInstance().getRoom(model.getRoomId());
        room.setRoomName(model.getRoomName());
        room.setMembers(model.getMembers());
        switch (response.getStatus()) {
            case SetRoomResponse.CREATE:
                room.onCreated();
                break;
            case SetRoomResponse.NORMAL:
                break;
            default:
                break;
        }
    }

    public void setRoomMember(Action action) {
        SetRoomMemberResponse response = (SetRoomMemberResponse)action;
        Room room = ChatManager.getInstance().getRoom(response.getRoomId());
        room.setMember(response.getMember(), response.getStatus());
    }

    public void broadcast(Action action) {
        BroadcastResponse response = (BroadcastResponse)action;
        Room room = ChatManager.getInstance().getRoom(response.getRoomId());
        room.onReceive(response.getSenderId(), response.getMessage());
    }

    public void setUser(Action action) {
        SetUserResponse response = (SetUserResponse)action;
        if (response.isZeroCode()) {
            Chat chat = ChatManager.getInstance().getChat(response.getUserId());
            chat.setUsername(response.getUsername());
        } else {
            FrmMain.getInstance().onUserNotFound();
        }
    }

    public void unicast(Action action) {
        UnicastResponse response = (UnicastResponse)action;
        Chat chat = ChatManager.getInstance().getChat(response.getSenderId());
        if (!chat.isInitialized()) {
            GetUserByIdRequest request = new GetUserByIdRequest(response.getSenderId());
            Client.getInstance().send(request);
        }
        chat.onReceive(response.getMessage());
    }

}
