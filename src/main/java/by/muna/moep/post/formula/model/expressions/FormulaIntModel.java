package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

public class FormulaIntModel implements IFormulaExpressionModel {
    private int value;

    public FormulaIntModel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.INT;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
