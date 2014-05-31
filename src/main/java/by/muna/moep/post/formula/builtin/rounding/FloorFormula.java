package by.muna.moep.post.formula.builtin.rounding;

import by.muna.moep.post.formula.FormulaRuntimeException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.types.FormulaFloatValue;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.Map;

public class FloorFormula implements IFormula {
    @Override
    public IFormulaValue eval(Map<String, IFormulaValue> args) throws FormulaRuntimeException {
        IFormulaValue value = args.get("0");

        if (value == null) {
            throw new FormulaRuntimeException("floor with null arg");
        }

        switch (value.getValueType()) {
        case INT: return value;
        case FLOAT:
            return new FormulaIntValue((long) Math.floor(((FormulaFloatValue) value).getValue()));
        default:
            throw new FormulaRuntimeException(
                "floor accepts INT or FLOAT, " + value.getValueType() + " given"
            );
        }
    }
}
