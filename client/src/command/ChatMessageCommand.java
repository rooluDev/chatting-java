package command;

/**
 * Chat Message Command
 */
public class ChatMessageCommand implements Command<String>{

    @Override
    public void execute(String message) {
        System.out.print("\r\033[K");
        System.out.println(message);
        System.out.print("입력 (종료하려면 'q' 입력 ): ");
    }
}
