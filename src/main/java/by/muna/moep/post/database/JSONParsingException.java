package by.muna.moep.post.database;

public class JSONParsingException extends Exception {
    public JSONParsingException() {
    }

    public JSONParsingException(String message) {
        super(message);
    }

    public JSONParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JSONParsingException(Throwable cause) {
        super(cause);
    }
}
