package by.muna.moep.post.formula.model.expressions;

import by.muna.moep.post.formula.model.FormulaBuiltInOperationTypeModel;
import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

import java.util.List;

public class FormulaBuiltInOperationModel implements IFormulaExpressionModel {
    private FormulaBuiltInOperationTypeModel type;
    private List<IFormulaExpressionModel> args;

    public FormulaBuiltInOperationModel(
        FormulaBuiltInOperationTypeModel type,
        List<IFormulaExpressionModel> args
    ) {
        this.type = type;
        this.args = args;
    }

    public FormulaBuiltInOperationTypeModel getBuiltInType() {
        return type;
    }

    public List<IFormulaExpressionModel> getArgs() {
        return args;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.BUILTIN_OPERATION;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('(');
        sb.append(this.type);

        for (IFormulaExpressionModel e : this.args) {
            sb.append(' ');
            sb.append(e.toString());
        }

        sb.append(')');

        return sb.toString();
    }
}
