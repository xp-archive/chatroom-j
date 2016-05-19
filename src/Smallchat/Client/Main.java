package Smallchat.Client;

import Smallchat.Action.Action;
import Smallchat.Server.ICallback;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 2333);
            Client.initInstance(socket);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return ;
        }

        FrmLogin frmLogin = FrmLogin.getInstance();
        frmLogin.setVisible(true);
    }

}
