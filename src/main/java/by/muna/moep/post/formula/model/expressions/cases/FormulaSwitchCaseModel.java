package by.muna.moep.post.formula.model.expressions.cases;

import by.muna.moep.post.formula.model.IFormulaExpressionModel;

public class FormulaSwitchCaseModel {
    private IFormulaExpressionModel matchingExpression;
    private IFormulaExpressionModel resultExpression;

    public FormulaSwitchCaseModel(
        IFormulaExpressionModel matchingExpression, IFormulaExpressionModel resultExpression)
    {
        this.matchingExpression = matchingExpression;
        this.resultExpression = resultExpression;
    }

    public IFormulaExpressionModel getMatchingExpression() {
        return matchingExpression;
    }

    public IFormulaExpressionModel getResultExpression() {
        return resultExpression;
    }
}
