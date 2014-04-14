package by.muna.moep.post.formula.parser;

import by.muna.moep.post.formula.FormulaException;

public class BadFormulaSyntaxException extends FormulaException {
    public BadFormulaSyntaxException() {
        super();
    }
    public BadFormulaSyntaxException(Throwable cause) {
        super(cause);
    }
    public BadFormulaSyntaxException(String message) {
        super(message);
    }
    public BadFormulaSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }
}
