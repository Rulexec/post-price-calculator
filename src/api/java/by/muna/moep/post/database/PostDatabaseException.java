package by.muna.moep.post.database;

public class PostDatabaseException extends Exception {
    public PostDatabaseException() {
        super();
    }
    public PostDatabaseException(String message) {
        super(message);
    }
    public PostDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
