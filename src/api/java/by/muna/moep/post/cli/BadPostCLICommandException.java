package by.muna.moep.post.cli;

public class BadPostCLICommandException extends Exception {
    public BadPostCLICommandException(String message) {
        super(message);
    }
    public BadPostCLICommandException(String message, Throwable cause) {
        super(message, cause);
    }
    public BadPostCLICommandException(Throwable cause) {
        super(cause);
    }
}
