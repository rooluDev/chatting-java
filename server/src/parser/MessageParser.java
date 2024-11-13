package parser;

/**
 * Client에서 받은 message parser
 */
public class MessageParser {

    /**
     * command + data로 이루어진 하나의 String에서 command만 추출
     *
     * @param message command + data
     * @return command
     */
    public String parseCommand(String message) {
        return message.split(" ", 2)[0];
    }

    /**
     * command + data로 이루어진 하나의 String에서 data만 추출
     *
     * @param message message command + data
     * @return data
     */
    public String parseData(String message) {
        try {
            return message.split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            return "";
        }

    }
}
