package socket;

import command.Command;
import command.CommandType;
import dto.Client;
import factory.CommandFactory;
import parser.MessageParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client에 연결된 Socket Receiver
 */
public class SocketReceiver extends Thread {

    private final Client client = new Client();
    private final MessageParser messageParser = new MessageParser();
    private final CommandFactory commandFactory = new CommandFactory();

    SocketReceiver(Socket socket) throws IOException {
        client.setDataOutputStream(new DataOutputStream(socket.getOutputStream()));
        client.setDataInputStream(new DataInputStream(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (client.getDataInputStream() != null) {
                // message 받기
                String receivedMessage = client.getDataInputStream().readUTF();
                // 메시지 파싱
                String receivedCommand = messageParser.parseCommand(receivedMessage);
                String receivedData = messageParser.parseData(receivedMessage);
                CommandType commandType = CommandType.fromString(receivedCommand);
                // 커맨드 실행
                Command command = commandFactory.createCommand(client, commandType);
                command.execute(receivedData);
            }
        } catch (IOException e) {
            System.out.println(client.getName() + " 접속 종료");
        }
    }
}
