package command;

import dto.Client;
import dto.Room;
import repository.RoomRepository;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

/**
 * CHAT_MESSAGE Command
 */
public class ChatMessageCommand implements Command<String> {

    private final Client client;
    private final RoomRepository roomRepository = RoomRepository.getInstance();

    public ChatMessageCommand(Client client) {
        this.client = client;
    }

    @Override
    public void execute(String message) {
        // 참여한 방 찾기
        Room participatedRoom = roomRepository.getRoomByClient(client);
        // 참여한 방의 참여자들 찾기
        Set<Client> participants = roomRepository.getParticipants(participatedRoom).orElse(Collections.emptySet());

        // 나를 제외한 참여자들에게 message 보내기
        for (Client participant : participants) {
            if (!participant.equals(client)) {
                try {
                    DataOutputStream dataOutputStream = participant.getDataOutputStream();
                    dataOutputStream.writeUTF("CHAT_MESSAGE");
                    dataOutputStream.writeUTF(client.getName());
                    dataOutputStream.writeUTF(message);
                } catch (IOException e) {
                    System.out.println(client.getName() + "의 메시지가 보내지지 않았습니다.");
                }
            }
        }
    }
}
