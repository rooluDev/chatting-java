package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket 서버 연결
 */
public class SocketConnection {

    private static final int PORT = 8888;

    /**
     * socket 서버 시작
     *
     * @throws IOException IOException
     */
    public void start() throws IOException {
        Socket socket = null;

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server Start");

        while (true){
            // 요청이 들올 때 까지 대기 후 연결
            socket = serverSocket.accept();
            System.out.println(socket.getInetAddress() + ":" + socket.getPort() + " has a connected");
            // serverReceiver 생성 및 시작
            SocketReceiver serverReceiver = new SocketReceiver(socket);
            serverReceiver.start();
        }
    }
}
