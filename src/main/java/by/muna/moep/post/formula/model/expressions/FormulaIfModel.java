package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

public class FormulaIfModel implements IFormulaExpressionModel {
    private IFormulaExpressionModel condition, thenExpression, elseExpression;

    public FormulaIfModel(
        IFormulaExpressionModel condition,
        IFormulaExpressionModel thenExpression,
        IFormulaExpressionModel elseExpression
    ) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public IFormulaExpressionModel getCondition() {
        return condition;
    }

    public IFormulaExpressionModel getThenExpression() {
        return thenExpression;
    }

    public IFormulaExpressionModel getElseExpression() {
        return elseExpression;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.IF;
    }

    @Override
    public String toString() {
        return "if " + this.condition.toString() +
            " then " + this.thenExpression.toString() +
            (this.elseExpression != null ? " else " + this.elseExpression.toString() :
                                           "");
    }
}
