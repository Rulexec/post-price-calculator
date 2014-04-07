package by.muna.moep.post.formula.types;

public class FormulaFloatValue implements IFormulaValue {
    private double value;

    public FormulaFloatValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.FLOAT;
    }
}
