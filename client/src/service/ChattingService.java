package service;

import command.CommandType;
import socket.SocketSender;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Chatting Service
 */
public class ChattingService {

    private final DataOutputStream dataOutputStream;

    public ChattingService(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    /**
     * 채팅 시작
     *
     * @throws IOException IOException
     */
    public void init() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("채팅 시작");
        System.out.print("이름을 입력하세요: ");
        String clientName = scanner.nextLine();

        sendMessage("CLIENT_SIGN_IN " + clientName);
    }

    /**
     * 채팅방 생성
     *
     * @throws InterruptedException InterruptedException
     * @throws IOException          IOException
     */
    public synchronized void createRoom() throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("채팅방 이름 입력: ");
        String roomName = scanner.nextLine();
        sendMessage(CommandType.CREATE_ROOM_SUCCESS.getCommand() + " " + roomName);

        this.wait();
    }

    /**
     * 채팅방 리스트 출력
     *
     * @throws InterruptedException InterruptedException
     * @throws IOException          IOException
     */
    public synchronized void selectRoom() throws InterruptedException, IOException {
        sendMessage("ROOM_LIST");
        this.wait();
    }

    /**
     * 채팅방 들어가기
     *
     * @param roomId 채팅방 sequence
     * @throws IOException IOException
     */
    public void joinRoom(Long roomId) throws IOException {
        sendMessage("JOIN_ROOM " + roomId);
    }

    /**
     * 채팅방 나가기
     */
    public synchronized void exitRoom() {
        notifyToMain();
        System.out.println("채팅방을 나왔습니다.");
    }

    /**
     * 채팅 시작
     */
    public void startChatSession() {
        SocketSender clientSender = new SocketSender(dataOutputStream);
        clientSender.start();
    }

    /**
     * 프로그램 종료
     */
    public void exit() throws IOException {
//        sendMessage("CLIENT_SIGN_OUT" + );
    }

    /**
     * Main Thread에게 알리기
     */
    public synchronized void notifyToMain() {
        this.notify();
    }

    private void sendMessage(String message) throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.writeUTF(message);
        }
    }
}
