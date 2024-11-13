package factory;

import command.*;
import service.ChattingService;

/**
 * Command Factory
 */
public class CommandFactory {

    private final ChattingService chattingService;

    public CommandFactory(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    /**
     * Command Type에 맞는 Command 객체 생성
     *
     * @param commandType Command Type
     * @return Command 객체
     */
    public Command createCommand(CommandType commandType) {
        switch (commandType) {
            case CREATE_ROOM_SUCCESS -> {
                return new CreateRoomCommand(chattingService);
            }
            case JOIN_ROOM -> {
                return new JoinRoomCommand(chattingService);
            }
            case ROOM_LIST -> {
                return new RoomListCommand(chattingService);
            }
            case CHAT_MESSAGE -> {
                return new ChatMessageCommand();
            }
            case PARTICIPANT_EXIT_ROOM -> {
                return new ParticipantExitRoomCommand();
            }
            case EXIT_ROOM_SUCCESS -> {
                return new ExitRoomCommand(chattingService);
            }
            case PARTICIPANT_JOIN_ROOM -> {
                return new ParticipantJoinRoomCommand();
            }
            default -> throw new IllegalArgumentException("유효하지 않은 명령어입니다: " + commandType);
        }
    }
}
