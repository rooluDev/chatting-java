package dto;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Client dto
 */
public class Client {
    private Long clientId;
    private String name;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;

    public Client() {
    }

    public Client(String name, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
        this.name = name;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }
}
