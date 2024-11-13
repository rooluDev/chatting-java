import common.Menu;
import service.ChattingService;
import socket.SocketConnection;
import socket.SocketReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        // 필요한 변수 선언 및 초기화
        Socket socket = null;
        ChattingService chattingService = null;
        SocketReceiver socketReceiver = null;

        try {
            // 소켓 생성 후 연결
            socket = new SocketConnection().getConnection();
            chattingService = new ChattingService(new DataOutputStream(socket.getOutputStream()));
            socketReceiver = new SocketReceiver(new DataInputStream(socket.getInputStream()), chattingService);
            chattingService.init();
            socketReceiver.start();
        } catch (IOException e) {
            System.out.println("서버에 연결하지 못했습니다.");
            return;
        }

        int firstMenu = 0;
        while (firstMenu != 3) {
            firstMenu = Menu.printMainMenu();
            switch (firstMenu) {
                case 1 -> {
                    try {
                        chattingService.createRoom();
                    } catch (InterruptedException e) {

                    } catch (IOException e) {

                    }
                }
                case 2 -> {
                    try {
                        chattingService.selectRoom();
                    } catch (InterruptedException e) {

                    } catch (IOException e) {

                    }
                }
                case 3 -> {
                    chattingService.exit();
                }
                default -> System.out.println("숫자를 1, 2 또는 3을 입력하세요");
            }
        }
    }
}