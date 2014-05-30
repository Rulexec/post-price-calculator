package by.muna.moep.post.formula.model.expressions.variables;

import by.muna.moep.post.formula.model.FormulaExpressionTypeModel;
import by.muna.moep.post.formula.model.FormulaVariableTypeModel;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;

public class FormulaVariableModel implements IFormulaVariableModel, IFormulaExpressionModel {
    private FormulaVariableTypeModel type;
    protected String name;

    public FormulaVariableModel(FormulaVariableTypeModel type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public FormulaVariableTypeModel getVariableType() {
        return this.type;
    }

    @Override
    public FormulaExpressionTypeModel getExpressionType() {
        return FormulaExpressionTypeModel.VARIABLE;
    }
}
