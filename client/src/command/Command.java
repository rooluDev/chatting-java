package command;

/**
 * Command Interface
 *
 * @param <T> data Type
 */
public interface Command<T> {

    /**
     * Command 실행
     *
     * @param data 데이터
     */
    void execute(T data);
}
