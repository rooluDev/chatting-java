package repository;

import dto.Client;
import dto.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Room repo
 */
public class RoomRepository {

    private final static RoomRepository instance = new RoomRepository();
    // Room 관리 repo
    private final static ConcurrentHashMap<Long, Room> store = new ConcurrentHashMap<>();
    // 참여자 관리 repo
    private final static ConcurrentHashMap<Room, Set<Client>> participantMap = new ConcurrentHashMap<>();
    // client room mapping repo
    private final static ConcurrentHashMap<Client, Room> clientRoomMap = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    private RoomRepository() {
    }

    public static RoomRepository getInstance() {
        return instance;
    }

    /**
     * 생성된 Room 저장
     *
     * @param room 생성된 Room
     */
    public void create(Room room){
        room.setRoomId(++sequence);
        store.put(room.getRoomId(), room);
    }

    /**
     * sequence로 Room 찾기
     *
     * @param roomId sequence
     * @return sequence에 맞는 Room
     */
    public Room getRoomById(Long roomId) {
        return store.get(roomId);
    }

    /**
     * 존재하는 room list 찾기
     *
     * @return 현재 존재하는 room list
     */
    public List<Room> getRooms() {
        return new ArrayList<>(store.values());
    }

    /**
     * 참여자 repo 추가
     *
     * @param room 입장할 room
     * @param client 입장할 client
     */
    public void addClientToRoom(Room room, Client client) {
        Set<Client> participants = participantMap.get(room);

        if (participants == null) {
            participants = ConcurrentHashMap.newKeySet();
            participantMap.put(room, participants);
        }
        participants.add(client);
        clientRoomMap.put(client, room);
    }

    /**
     * Client가 입장한 Room 찾기
     *
     * @param client 입장한 client
     * @return client가 입장한 room
     */
    public Room getRoomByClient(Client client) {
        return clientRoomMap.get(client);
    }

    /**
     * room에 있는 참여자 찾기
     *
     * @param room room
     * @return 참여자들
     */
    public Optional<Set<Client>> getParticipants(Room room) {
        return Optional.ofNullable(participantMap.get(room));
    }

    /**
     * 참여자 삭제 및 방 삭제
     *
     * @param client 나가는 client
     */
    public void exitRoom(Client client) {
        Room room = clientRoomMap.get(client);
        Set<Client> participants = participantMap.get(room);
        if (participants.size() < 2) {
            participants.remove(room);
            delete(room);
        } else {
            participants.remove(client);
        }
        clientRoomMap.remove(client);
    }

    /**
     * room 삭제
     *
     * @param room 삭제할 room
     */
    private void delete(Room room) {
        store.remove(room.getRoomId());
    }
}
