import socket.SocketConnection;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new SocketConnection().start();
        } catch (IOException e) {
            System.out.println("Server Error");
        }
    }
}