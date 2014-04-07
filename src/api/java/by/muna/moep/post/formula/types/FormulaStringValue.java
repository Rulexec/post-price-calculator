package by.muna.moep.post.formula.types;

public class FormulaStringValue implements IFormulaValue {
    private String value;

    public FormulaStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.STRING;
    }
}
