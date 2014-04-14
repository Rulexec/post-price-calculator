package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

public class FormulaExpressionModel<T> implements IFormulaExpressionModel {
    private FormulaExpressionTypeModel type;
    protected T value;

    public FormulaExpressionModel(FormulaExpressionTypeModel type, T value) {
        this.type = type;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return this.type;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
