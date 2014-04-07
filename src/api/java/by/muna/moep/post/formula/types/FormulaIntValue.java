package by.muna.moep.post.formula.types;

public class FormulaIntValue implements IFormulaValue {
    private int value;

    public FormulaIntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.INT;
    }
}
