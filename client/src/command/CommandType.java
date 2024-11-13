package command;

import java.util.Arrays;

/**
 * Command Type Enum
 */
public enum CommandType {

    CREATE_ROOM_SUCCESS("CREATE_ROOM_SUCCESS"),
    JOIN_ROOM("JOIN_ROOM"),
    ROOM_LIST("ROOM_LIST"),
    CHAT_MESSAGE("CHAT_MESSAGE"),
    PARTICIPANT_EXIT_ROOM("PARTICIPANT_EXIT_ROOM"),
    EXIT_ROOM_SUCCESS("EXIT_ROOM_SUCCESS"),
    PARTICIPANT_JOIN_ROOM("PARTICIPANT_JOIN_ROOM");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    public String getCommand() {
        return commandString;
    }

    /**
     * Command String을 Enum으로 변환
     *
     * @param command command 문자열
     * @return Command
     */
    public static CommandType parseCommandType(String command) {
        return Arrays.stream(CommandType.values())
                .filter(type -> type.commandString.equalsIgnoreCase(command))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 명령어입니다: " + command));
    }
}

