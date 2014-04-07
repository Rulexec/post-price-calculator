package by.muna.moep.post;

public class PostException extends Exception {
    public PostException() {
        super();
    }
    public PostException(String message) {
        super(message);
    }
    public PostException(String message, Throwable cause) {
        super(message, cause);
    }
}
