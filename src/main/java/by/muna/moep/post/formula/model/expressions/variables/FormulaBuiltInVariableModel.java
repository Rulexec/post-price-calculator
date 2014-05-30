package by.muna.moep.post.formula.model.expressions.variables;

import by.muna.moep.post.formula.model.FormulaVariableTypeModel;

public class FormulaBuiltInVariableModel extends FormulaVariableModel {
    public FormulaBuiltInVariableModel(String name) {
        super(FormulaVariableTypeModel.BUILTIN_VARIABLE, name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
