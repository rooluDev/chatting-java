package socket;

import command.Command;
import command.CommandType;
import dto.Room;
import factory.CommandFactory;
import service.ChattingService;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Socket Receiver
 */
public class SocketReceiver extends Thread {

    private final DataInputStream dataInputStream;
    private final ChattingService chattingService;

    public SocketReceiver(DataInputStream dataInputStream, ChattingService chattingService) {
        this.dataInputStream = dataInputStream;
        this.chattingService = chattingService;
    }

    @Override
    public void run() {
        CommandFactory commandFactory = new CommandFactory(chattingService);

        while (dataInputStream != null) {
            try {
                // command 받기
                String receivedCommand = dataInputStream.readUTF();
                CommandType commandType = CommandType.parseCommandType(receivedCommand);
                Command command = commandFactory.createCommand(commandType);

                switch (commandType) {
                    case JOIN_ROOM, PARTICIPANT_EXIT_ROOM, PARTICIPANT_JOIN_ROOM -> {
                        String data = dataInputStream.readUTF();
                        command.execute(data);
                    }
                    case CREATE_ROOM_SUCCESS -> {
                        Long roomId = dataInputStream.readLong();
                        command.execute(roomId);
                    }
                    case ROOM_LIST -> {
                        int length = dataInputStream.readInt();
                        byte[] byteArray = new byte[length];
                        dataInputStream.readFully(byteArray);
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        List<Room> rooms = (List<Room>) objectInputStream.readObject();
                        command.execute(rooms);
                    }
                    case CHAT_MESSAGE -> {
                        String senderName = dataInputStream.readUTF();
                        String message = dataInputStream.readUTF();
                        command.execute(senderName + ": " + message);
                    }
                    case EXIT_ROOM_SUCCESS -> {
                        command.execute(null);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("sever out");
                break;
            }
        }
    }
}
