package by.muna.moep.post.formula;

public class FormulaRuntimeException extends FormulaException {
    public FormulaRuntimeException() {
        super();
    }
    public FormulaRuntimeException(Throwable ex) {
        super(ex);
    }
    public FormulaRuntimeException(String message) {
        super(message);
    }
    public FormulaRuntimeException(String message, Throwable ex) {
        super(message, ex);
    }
}
