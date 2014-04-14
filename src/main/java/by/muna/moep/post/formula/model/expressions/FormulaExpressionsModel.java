package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

import java.util.List;

public class FormulaExpressionsModel implements IFormulaExpressionModel {
    private List<IFormulaExpressionModel> expressions;

    public FormulaExpressionsModel(List<IFormulaExpressionModel> expressions) {
        this.expressions = expressions;
    }

    public List<IFormulaExpressionModel> getExpressions() {
        return expressions;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.EXPRESSIONS;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean isFirst = true;
        for (IFormulaExpressionModel e : this.expressions) {
            if (isFirst) isFirst = false;
            else sb.append(", ");

            sb.append(e.toString());
        }

        return sb.toString();
    }
}
