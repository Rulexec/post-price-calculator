package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.moep.post.formula.model.expressions.variables.FormulaUserVariableModel;

public class FormulaAssignmentModel implements IFormulaExpressionModel {
    private FormulaUserVariableModel variable;
    private IFormulaExpressionModel expression;

    public FormulaAssignmentModel(
        FormulaUserVariableModel variable,
        IFormulaExpressionModel expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    public FormulaUserVariableModel getVariable() {
        return variable;
    }

    public IFormulaExpressionModel getExpression() {
        return expression;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.ASSIGNMENT;
    }

    @Override
    public String toString() {
        return this.variable + " = " + this.expression;
    }
}
