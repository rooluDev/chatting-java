package command;

import service.ChattingService;

/**
 * Exit Room Command
 */
public class ExitRoomCommand implements Command<Object>{

    private final ChattingService chattingService;

    public ExitRoomCommand(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @Override
    public void execute(Object data) {
        chattingService.exitRoom();
    }
}
