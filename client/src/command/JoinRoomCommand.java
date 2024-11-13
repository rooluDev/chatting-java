package command;

import service.ChattingService;

/**
 * Join Room Command
 */
public class JoinRoomCommand implements Command<String> {

    private final ChattingService chattingService;

    public JoinRoomCommand(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @Override
    public void execute(String roomName) {
        System.out.println(roomName + "방에 입장했습니다.");
        // sender thread 실행
        chattingService.startChatSession();
    }
}
