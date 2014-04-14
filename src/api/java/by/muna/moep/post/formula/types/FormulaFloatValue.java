package by.muna.moep.post.formula.types;

import by.muna.moep.post.formula.FormulaRuntimeException;

public class FormulaFloatValue implements IFormulaValue {
    private double value;

    public FormulaFloatValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public IFormulaValue pow(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaFloatValue) {
            return new FormulaFloatValue(
                Math.pow(this.value, ((FormulaFloatValue) o).value)
            );
        } else if (o instanceof FormulaIntValue) {
            return new FormulaFloatValue(
                Math.pow(this.value, ((FormulaIntValue) o).getValue())
            );
        } else {
            throw new FormulaRuntimeException("pow float not applicable to: " + o);
        }
    }

    @Override
    public IFormulaValue add(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaFloatValue) {
            return new FormulaFloatValue(
                this.value + ((FormulaFloatValue) o).value
            );
        } else if (o instanceof FormulaIntValue) {
            return new FormulaFloatValue(
                this.value + ((FormulaIntValue) o).getValue()
            );
        } else {
            throw new FormulaRuntimeException("add float not applicable to: " + o);
        }
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.FLOAT;
    }
}
