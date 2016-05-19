package Smallchat.Server;

import Smallchat.Action.Action;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean active = false;
    private ICallback onReceive = null;
    private ICallback onClose = null;

    private User user = null;

    public Client(Socket socket, boolean inputFirst) {
        this.socket = socket;
        try {
            if (inputFirst) {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
            } else {
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            close();
        }
    }

    public void start() {
        active = true;
        new Thread(new ReceivingThread()).start();
    }

    public void close() {
        if (!active) return;
        active = false;
        try {
            if (onClose != null) onClose.execute();
            if (!socket.isInputShutdown()) input.close();
            if (!socket.isOutputShutdown()) output.close();
            if (!socket.isClosed()) socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            onReceive = null;
            onClose = null;
            input = null;
            output = null;
            socket = null;
        }
    }

    public User getUser() {
        return user;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    public void setUser(User user) {
        this.user = user;
        System.out.println(String.format("%d(%s) online", user.getUserId(), user.getUsername()));
        setOnClose(new ICallback() {
            @Override
            public int execute(Object... args) {
                user.onExit();
                return 0;
            }
        });
    }

    public void setOnReceive(ICallback callback) {
        onReceive = callback;
    }

    public void setOnClose(ICallback callback) {
        onClose = callback;
    }

    public boolean isActive() { return active; }

    public <T extends Action> boolean send(Action obj) {
        if (!active) return false;
        try {
            output.writeObject(obj);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            close();
            return false;
        }
    }

    public <T extends Action> T receive() {
        if (!active) return null;
        try {
            return (T) input.readObject();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        } catch (IOException ex) {
            //ex.printStackTrace();
            close();
            return null;
        }
    }

    private class ReceivingThread implements Runnable {
        @Override
        public void run() {
            while (active) {
                Action action = receive();
                if (onReceive != null) onReceive.execute(action);
            }
        }
    }

}
