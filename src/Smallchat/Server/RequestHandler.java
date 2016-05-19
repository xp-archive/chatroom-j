package Smallchat.Server;

import Smallchat.Action.Action;
import Smallchat.Action.Request.*;
import Smallchat.Action.Response.*;
import Smallchat.Model.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler {

    private Client client;

    public RequestHandler(Client client) {
        this.client = client;
        client.setOnReceive(new ICallback() {
            @Override
            public int execute(Object... args) {
                Action action = (Action)args[0];
                try {
                    int userId = client.isAuthenticated() ? client.getUser().getUserId() : 0;
                    System.out.println(String.format("receive %d.%s()", userId, action.getAction()));
                    Method myMethod = RequestHandler.class.getMethod(action.getAction(), Action.class);
                    myMethod.invoke(RequestHandler.this, action);
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

    private void setUser(int userId, String username) {
        client.setUser(new User(client, new UserModel(userId, username)));
    }

    public void register(Action action) {
        RegisterRequest request = (RegisterRequest) action;
        int userId = Account.getInstance().register(request.getUsername(), request.getPassword());
        RegisterResponse response;
        if (userId != 0) {
            setUser(userId, request.getUsername());
            response = new RegisterResponse(0, userId, request.getUsername());
        } else {
            response = new RegisterResponse(1, 0, request.getUsername());
        }
        client.send(response);
    }

    public void login(Action action) {
        LoginRequest request = (LoginRequest) action;
        int userId = Account.getInstance().login(request.getUsername(), request.getPassword());
        LoginResponse response;
        if (userId != 0) {
            setUser(userId, request.getUsername());
            response = new LoginResponse(0, userId, request.getUsername());
        } else {
            response = new LoginResponse(1, 0, request.getUsername());
        }
        client.send(response);
    }

    public void getRooms(Action action) {
        GetRoomsRequest request = (GetRoomsRequest)action;

        SetRoomsResponse response = new SetRoomsResponse(RoomModel.toMap(client.getUser().getRooms()));
        client.send(response);
    }

    public void getRoom(Action action) {
        GetRoomRequest request = (GetRoomRequest)action;
        Room room = RoomManager.getInstance().find(request.getRoomId());
        if (room == null) {
            return ;
        }

        SetRoomResponse response = new SetRoomResponse(SetRoomResponse.NORMAL, new RoomModel(room));
        client.send(response);
    }

    public void createRoom(Action action) {
        CreateRoomRequest request = (CreateRoomRequest)action;
        Room room = RoomManager.getInstance().add(request.getRoomName(), client.getUser());

        SetRoomResponse response = new SetRoomResponse(SetRoomResponse.CREATE, new RoomModel(room));
        client.send(response);
    }

    public void joinRoom(Action action) {
        JoinRoomRequest request = (JoinRoomRequest)action;
        Room room = RoomManager.getInstance().find(request.getRoomId());
        if (room == null) {
            return ;
        }
        if (room.addMember(client.getUser())) {
            SetRoomResponse response = new SetRoomResponse(SetRoomResponse.JOIN, new RoomModel(room));
            client.send(response);
        }
    }

    public void quitRoom(Action action) {
        QuitRoomRequest request = (QuitRoomRequest)action;
        Room room = RoomManager.getInstance().find(request.getRoomId());
        if (room == null) {
            return ;
        }

        room.removeMember(client.getUser().getUserId());
    }

    public void getUserByName(Action action) {
        GetUserByNameRequest request = (GetUserByNameRequest)action;
        User dst = UserManager.getInstance().find(request.getUsername());
        SetUserResponse response;
        if (dst != null) {
            response = new SetUserResponse(0);
            response.setUser(dst.getUserId(), dst.getUsername());
        } else {
            response = new SetUserResponse(1);
            response.setUser(request.getUsername());
        }
        client.send(response);
    }

    public void getUserById(Action action) {
        GetUserByIdRequest request = (GetUserByIdRequest)action;
        User dst = UserManager.getInstance().find(request.getUserId());
        SetUserResponse response;
        if (dst != null) {
            response = new SetUserResponse(0);
            response.setUser(dst.getUserId(), dst.getUsername());
        } else {
            response = new SetUserResponse(1);
            response.setUser(request.getUserId());
        }
        client.send(response);
    }

    public void unicast(Action action) {
        UnicastRequest request = (UnicastRequest)action;
        User dst = UserManager.getInstance().find(request.getUserId());
        if (dst == null) {
            return ;
        }

        UnicastResponse response = new UnicastResponse(client.getUser().getUserId(), request.getMessage());
        dst.send(response);
    }

    public void broadcast(Action action) {
        BroadcastRequest request = (BroadcastRequest)action;
        Room room = RoomManager.getInstance().find(request.getRoomId());
        if (room == null) {
            return ;
        }

        BroadcastResponse response = new BroadcastResponse(client.getUser().getUserId(), room.getRoomId(), request.getMessage());
        room.broadcast(response);
    }

}