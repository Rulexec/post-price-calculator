package by.muna.moep.post.formula.types;

public class FormulaBooleanValue implements IFormulaValue {
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
