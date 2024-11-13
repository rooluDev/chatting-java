package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Socket Sender
 */
public class SocketSender extends Thread {

    private final DataOutputStream dataOutputStream;

    public SocketSender(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (dataOutputStream != null) {
            System.out.print("입력 (종료하려면 'q'): ");
            String message = scanner.nextLine();
            System.out.print("\033[F");
            System.out.print("\r\033[K");
            System.out.println("나 : " + message);
            try {
                if ("q".equalsIgnoreCase(message)) {
                    sendMessage("EXIT_ROOM");
                    break;
                }
                this.sendMessage("CHAT_MESSAGE " + message);
            } catch (IOException e) {
                System.out.println("메시지를 보내지 못했습니다.");
            }
        }
    }

    private void sendMessage(String message) throws IOException {
        if (dataOutputStream != null) {
            dataOutputStream.writeUTF(message);
        }
    }
}
