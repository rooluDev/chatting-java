package command;

import dto.Client;
import dto.Room;
import repository.RoomRepository;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * ROOM_LIST Command
 */
public class RoomListCommand implements Command {

    private final RoomRepository roomRepository = RoomRepository.getInstance();
    private final Client client;

    public RoomListCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(Object data) {
        try {
            // room list 정보
            List<Room> rooms = roomRepository.getRooms();

            // 직렬화
            byte[] roomsByteArray = serializeRooms(rooms);

            DataOutputStream dataOutputStream = client.getDataOutputStream();
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF("ROOM_LIST");
                dataOutputStream.writeInt(roomsByteArray.length);
                dataOutputStream.write(roomsByteArray);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] serializeRooms(List<Room> rooms) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(rooms);
        objectOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }
}
