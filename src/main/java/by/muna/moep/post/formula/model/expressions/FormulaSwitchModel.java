package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.moep.post.formula.model.expressions.cases.FormulaSwitchCaseModel;

import java.util.List;

public class FormulaSwitchModel implements IFormulaExpressionModel {
    private IFormulaExpressionModel expression;
    private List<FormulaSwitchCaseModel> cases;
    private IFormulaExpressionModel defaultCase;

    public FormulaSwitchModel(
        IFormulaExpressionModel expression,
        List<FormulaSwitchCaseModel> cases,
        IFormulaExpressionModel defaultCase
    )
    {
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    public IFormulaExpressionModel getExpression() {
        return expression;
    }

    public List<FormulaSwitchCaseModel> getCases() {
        return cases;
    }

    public IFormulaExpressionModel getDefaultCase() {
        return defaultCase;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.SWITCH;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("switch ");
        sb.append(this.expression.toString());
        sb.append(": ");

        boolean isFirst = true;

        for (FormulaSwitchCaseModel switchCase : this.cases) {
            if (isFirst) isFirst = false;
            else sb.append(", ");

            sb.append("case ");
            sb.append(switchCase.getMatchingExpression().toString());
            sb.append(": ");
            sb.append(switchCase.getResultExpression().toString());
        }

        if (this.defaultCase != null) {
            sb.append(' ');
            sb.append("default: ");
            sb.append(this.defaultCase.toString());
        }

        return sb.toString();
    }
}
