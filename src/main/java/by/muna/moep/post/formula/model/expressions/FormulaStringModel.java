package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;

public class FormulaStringModel extends FormulaExpressionModel<String> {
    public FormulaStringModel(String value) {
        super(FormulaExpressionTypeModel.STRING, value);
    }

    @Override
    public String toString() {
        return '\'' + this.value + '\'';
    }
}
