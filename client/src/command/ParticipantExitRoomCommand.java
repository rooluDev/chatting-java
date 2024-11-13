package command;

/**
 * Participant Exit Room Command
 */
public class ParticipantExitRoomCommand implements Command<String> {

    @Override
    public void execute(String data) {
        System.out.print("\r\033[K");
        System.out.println(data + "님이 나가셨습니다.");
        System.out.print("입력 (종료하려면 'q' 입력 ): ");
    }
}
