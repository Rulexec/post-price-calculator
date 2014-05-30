package by.muna.moep.post.formula.model.expressions.variables;

import by.muna.moep.post.formula.model.FormulaVariableTypeModel;

public class FormulaUserVariableModel extends FormulaVariableModel {
    public FormulaUserVariableModel(String name) {
        super(FormulaVariableTypeModel.USER_VARIABLE, name);
    }

    @Override
    public String toString() {
        return '$' + this.name;
    }
}
