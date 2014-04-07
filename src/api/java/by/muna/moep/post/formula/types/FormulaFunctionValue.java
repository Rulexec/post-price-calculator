package by.muna.moep.post.formula.types;

import by.muna.moep.post.formula.IFormula;

public class FormulaFunctionValue implements IFormulaValue {
    private IFormula value;

    public FormulaFunctionValue(IFormula value) {
        this.value = value;
    }

    public IFormula getValue() {
        return this.value;
    }

    @Override
    public FormulaValueType getValueType() {
        return FormulaValueType.BOOLEAN;
    }
}
