package command;

/**
 * Command Type
 */
public enum CommandType {
    CREATE_ROOM("CREATE_ROOM"),
    CLIENT_SIGN_IN("CLIENT_SIGN_IN"),
    CLIENT_SIGN_OUT("CLIENT_SIGN_OUT"),
    CHAT_MESSAGE("CHAT_MESSAGE"),
    ROOM_LIST("ROOM_LIST"),
    JOIN_ROOM("JOIN_ROOM"),
    EXIT_ROOM("EXIT_ROOM");

    private final String commandString;

    CommandType(String commandString) {
        this.commandString = commandString;
    }

    public String getCommand() {
        return commandString;
    }

    public static CommandType fromString(String command) {
        for (CommandType type : CommandType.values()) {
            if (type.commandString.equalsIgnoreCase(command)) {
                return type;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 명령어입니다: " + command);
    }
}
