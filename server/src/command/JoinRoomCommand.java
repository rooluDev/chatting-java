package command;

import dto.Client;
import dto.Room;
import repository.RoomRepository;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * JOIN_ROOM Command
 */
public class JoinRoomCommand implements Command<String> {

    private final Client client;
    private final RoomRepository roomRepository = RoomRepository.getInstance();

    public JoinRoomCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(String roomId) {
        // roomId로 방 정보
        Long roomIdL = Long.parseLong(roomId);
        Room selectedRoom = roomRepository.getRoomById(roomIdL);
        // 참여자들 정보
        Set<Client> participants = roomRepository.getParticipants(selectedRoom).orElse(Collections.emptySet());
        // repo에 추가
        roomRepository.addClientToRoom(selectedRoom, client);

        DataOutputStream dataOutputStream = client.getDataOutputStream();
        try {
            for (Client participant : participants) {
                // 참여자들에게 입장 메시지 보내기
                if (!participant.equals(client) && dataOutputStream != null) {
                    DataOutputStream participantDataOutPutStream = participant.getDataOutputStream();
                    participantDataOutPutStream.writeUTF("PARTICIPANT_JOIN_ROOM");
                    participantDataOutPutStream.writeUTF(client.getName());
                }
            }
            if (dataOutputStream != null) {
                dataOutputStream.writeUTF("JOIN_ROOM");
                dataOutputStream.writeUTF(selectedRoom.getRoomName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
