package command;

import service.ChattingService;

import java.io.IOException;

/**
 * Create Room Command
 */
public class CreateRoomCommand implements Command<Long> {

    private final ChattingService chattingService;

    public CreateRoomCommand(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @Override
    public void execute(Long roomId){
        try {
            chattingService.joinRoom(roomId);
        } catch (IOException e) {
            System.out.println("서버 에러");
        }
    }
}
