package by.muna.moep.post.formula.types;

import by.muna.moep.post.formula.FormulaRuntimeException;

public class FormulaIntValue implements IFormulaValue {
    private long value;

    public FormulaIntValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public IFormulaValue add(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaIntValue) {
            return new FormulaIntValue(
                this.value + ((FormulaIntValue) o).value
            );
        } else if (o instanceof FormulaFloatValue) {
            return new FormulaFloatValue(
                this.value + ((FormulaFloatValue) o).getValue()
            );
        } else {
            throw new FormulaRuntimeException("add int not applicable to: " + o);
        }
    }

    @Override
    public IFormulaValue multiply(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaIntValue) {
            return new FormulaIntValue(
                this.value * ((FormulaIntValue) o).value
            );
        } else if (o instanceof FormulaFloatValue) {
            return new FormulaFloatValue(
                this.value * ((FormulaFloatValue) o).getValue()
            );
        } else {
            throw new FormulaRuntimeException("add int not applicable to: " + o);
        }
    }

    @Override
    public IFormulaValue pow(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaIntValue) {
            return new FormulaIntValue(
                (long) Math.pow(this.value, ((FormulaIntValue) o).value)
            );
        } else if (o instanceof FormulaFloatValue) {
            return new FormulaFloatValue(
                Math.pow(this.value, ((FormulaFloatValue) o).getValue())
            );
        } else {
            throw new FormulaRuntimeException("pow int not applicable to: " + o);
        }
    }

    @Override
    public FormulaBooleanValue isEqual(IFormulaValue o) throws FormulaRuntimeException {
        if (o instanceof FormulaFloatValue) {
            throw new FormulaRuntimeException("isEquals int not implemented for float");
        }

        if (!(o instanceof FormulaIntValue)) return FormulaBooleanValue.FALSE;

        return this.value == ((FormulaIntValue) o).value ?
            FormulaBooleanValue.TRUE : FormulaBooleanValue.FALSE;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.INT;
    }
}
