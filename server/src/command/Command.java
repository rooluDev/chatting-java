package command;

/**
 * Command Interface
 *
 * @param <T> data Type
 */
public interface Command<T> {
    void execute(T data);
}
