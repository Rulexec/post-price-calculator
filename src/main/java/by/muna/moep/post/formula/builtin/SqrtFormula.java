package by.muna.moep.post.formula.builtin;

import by.muna.moep.post.formula.FormulaRuntimeException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.types.FormulaFloatValue;
import by.muna.moep.post.formula.types.FormulaFunctionValue;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.Map;

public class SqrtFormula implements IFormula {
    public static final FormulaFunctionValue SQRT_FUNCTION = new FormulaFunctionValue(new SqrtFormula());

    @Override
    public IFormulaValue eval(Map<String, IFormulaValue> args) throws FormulaRuntimeException {
        IFormulaValue value = args.get("0");

        if (value == null) {
            throw new FormulaRuntimeException("sqrt with null arg");
        }

        switch (value.getValueType()) {
        case INT:
            return new FormulaIntValue((long) Math.sqrt(((FormulaIntValue) value).getValue()));
        case FLOAT:
            return new FormulaFloatValue(Math.sqrt(((FormulaFloatValue) value).getValue()));
        default:
            throw new FormulaRuntimeException(
                "sqrt accepts INT or FLOAT, " + value.getValueType() + " given"
            );
        }
    }
}
