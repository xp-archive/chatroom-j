package Smallchat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        System.out.println(String.format("Server.listen()"));
        while (true) {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket, true);
            RequestHandler handler = new RequestHandler(client);
            client.start();
        }
    }

}
