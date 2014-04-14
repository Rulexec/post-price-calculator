package by.muna.moep.post.formula.types;

public class FormulaBooleanValue implements IFormulaValue {
    public static final FormulaBooleanValue TRUE = new FormulaBooleanValue(true);
    public static final FormulaBooleanValue FALSE = new FormulaBooleanValue(false);

    private boolean value;

    public FormulaBooleanValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return this.value;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.BOOLEAN;
    }
}
