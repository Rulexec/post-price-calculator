package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.moep.post.formula.model.expressions.variables.IFormulaVariableModel;

import java.util.List;

public class FormulaFunctionCallModel implements IFormulaExpressionModel {
    private IFormulaExpressionModel function;
    private List<IFormulaExpressionModel> args;

    public FormulaFunctionCallModel(
        IFormulaExpressionModel function, List<IFormulaExpressionModel> args)
    {
        this.function = function;
        this.args = args;
    }

    public IFormulaExpressionModel getFunction() {
        return function;
    }

    public List<IFormulaExpressionModel> getArgs() {
        return args;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.FUNCTION_CALL;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean surroundWithBraces = !(this.function instanceof IFormulaVariableModel);

        if (surroundWithBraces) sb.append('(');
        sb.append(this.function.toString());
        if (surroundWithBraces) sb.append(')');

        sb.append('(');
        boolean isFirst = true;
        for (IFormulaExpressionModel e : this.args) {
            if (isFirst) isFirst = false;
            else sb.append(", ");

            sb.append(e.toString());
        }
        sb.append(')');

        return sb.toString();
    }
}
