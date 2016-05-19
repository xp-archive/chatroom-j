package Smallchat.Server;

import java.io.IOException;

public class Main {

    public static void main(String argv[]) {
        try {
            Server server = new Server(2333);
            server.accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
