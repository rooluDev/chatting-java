package socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Socket Server Connection
 */
public class SocketConnection {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 8888;

    /**
     * Server 연결
     *
     * @return 연결된 Socket 객체
     * @throws IOException IOException
     */
    public Socket getConnection() throws IOException {
        return new Socket(SERVER_IP, PORT);
    }
}
