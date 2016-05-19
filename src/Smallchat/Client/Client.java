package Smallchat.Client;

import Smallchat.Action.Action;
import Smallchat.Server.ICallback;

import java.net.Socket;

public class Client {

    private static Client instance;

    public static Client getInstance() {
        return instance;
    }

    public static void initInstance(Socket socket) {
        instance = new Client(socket);
    }

    Smallchat.Server.Client client;

    public Client(Socket socket) {
        client = new Smallchat.Server.Client(socket, false);

        client.setOnClose(new ICallback() {
            @Override
            public int execute(Object... args) {
                System.out.println(String.format("Connection Closed."));
                System.exit(1);
                return 0;
            }
        });

        ResponseHandler handler = new ResponseHandler(this);
        client.start();
    }

    public <T extends Action> boolean send(T model) {
        System.out.println(String.format("send %s()", model.getAction()));
        return client.send(model);
    }

    public void setOnReceive(ICallback callback) {
        client.setOnReceive(callback);
    }

}
