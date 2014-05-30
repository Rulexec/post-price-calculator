package by.muna.moep.post.formula;

/**
 * Исключение бросается {@link by.muna.moep.post.formula.IFormulaBuilder}'ом
 * в случае недоразрешённых зависимостях формулы.
 */
public class BadFormulaException extends FormulaException {
    public BadFormulaException() {
        super();
    }
    public BadFormulaException(String message) {
        super(message);
    }
}
