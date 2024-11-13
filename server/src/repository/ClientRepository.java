package repository;

import dto.Client;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Client Repo
 */
public class ClientRepository {

    public static ConcurrentHashMap<Long, Client> store = new ConcurrentHashMap<>();
    private final static ClientRepository instance = new ClientRepository();
    private static Long sequence = 0L;

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        return instance;
    }

    /**
     * client 저장
     *
     * @param client 접속한 client
     */
    public void add(Client client){
        client.setClientId(++sequence);
        store.put(client.getClientId(), client);
    }

    /**
     * client 삭제
     *
     * @param client 접속 종료한 client
     */
    public void delete(Client client){
        store.remove(client.getClientId());
    }
}
