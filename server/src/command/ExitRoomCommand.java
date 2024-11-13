package command;

import dto.Client;
import dto.Room;
import repository.RoomRepository;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * EXIT_ROOM Command
 */
public class ExitRoomCommand implements Command {

    private final Client client;
    private final RoomRepository repository = RoomRepository.getInstance();

    public ExitRoomCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(Object data) {
        // 참여한 Room 데이터
        Room participatedRoom = repository.getRoomByClient(client);
        // 참여자들 데이터
        Set<Client> participants = repository.getParticipants(participatedRoom).orElse(Collections.emptySet());
        // repo에서 삭제
        repository.exitRoom(client);

        String exitedParticipantName = client.getName();
        try {
            client.getDataOutputStream().writeUTF("EXIT_ROOM_SUCCESS");
            // 참여자들에게 자신이 나간 메시지 보내기
            for (Client participant : participants) {
                DataOutputStream dataOutputStream = participant.getDataOutputStream();
                if (!participant.equals(client) && dataOutputStream != null) {
                    dataOutputStream.writeUTF("PARTICIPANT_EXIT_ROOM");
                    dataOutputStream.writeUTF(exitedParticipantName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
