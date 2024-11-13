package factory;

import command.*;
import dto.Client;

/**
 * Command Factory
 */
public class CommandFactory {

    /**
     * commandType에 맞는 Command 생성
     *
     * @param client data를 보낸 client
     * @param commandType commandType
     * @return command
     */
    public Command createCommand(Client client, CommandType commandType) {
        switch (commandType) {
            case CREATE_ROOM -> {
                return new CreateRoomCommand(client);
            }
            case CHAT_MESSAGE -> {
                return new ChatMessageCommand(client);
            }
            case CLIENT_SIGN_IN -> {
                return new ClientSignInCommand(client);
            }
            case CLIENT_SIGN_OUT -> {
                return new ClientSignOutCommand(client);
            }
            case ROOM_LIST -> {
                return new RoomListCommand(client);
            }
            case JOIN_ROOM -> {
                return new JoinRoomCommand(client);
            }
            case EXIT_ROOM -> {
                return new ExitRoomCommand(client);
            }
            default -> {
                throw new IllegalArgumentException("유효하지 않은 명령어입니다: " + commandType);
            }
        }
    }
}
