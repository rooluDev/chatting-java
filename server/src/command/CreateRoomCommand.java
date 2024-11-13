package command;

import dto.Client;
import dto.Room;
import repository.RoomRepository;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * CREATE_ROOM Command
 */
public class CreateRoomCommand implements Command<String> {

    private final Client client;
    private final RoomRepository roomRepository = RoomRepository.getInstance();

    public CreateRoomCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(String roomName) {
        // Room 객체 생성
        Room room = new Room();
        room.setRoomName(roomName);

        // repo에 추가
        roomRepository.create(room);

        // 생성 성공 메시지 보내기
        try {
            DataOutputStream dataOutputStream = client.getDataOutputStream();
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF("CREATE_ROOM_SUCCESS");
                dataOutputStream.writeLong(room.getRoomId());
            }
        } catch (IOException e) {
            // TODO : 방 생성 실패시 실패 메시지 보내기
            e.printStackTrace();
        }
    }
}
