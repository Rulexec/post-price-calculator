package by.muna.moep.post.formula.types;

import by.muna.moep.post.formula.FormulaRuntimeException;

public class FormulaStringValue implements IFormulaValue {
    private String value;

    public FormulaStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public FormulaBooleanValue isEqual(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaStringValue) {
            return this.value.equals(((FormulaStringValue) o).getValue()) ?
                FormulaBooleanValue.TRUE : FormulaBooleanValue.FALSE;
        } else {
            return FormulaBooleanValue.FALSE;
        }
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.STRING;
    }
}
