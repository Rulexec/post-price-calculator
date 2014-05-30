package by.muna.moep.post.formula.model.expressions.variables;

import by.muna.moep.post.formula.model.FormulaVariableTypeModel;

public class FormulaExternalVariableModel extends FormulaVariableModel {
    public FormulaExternalVariableModel(String name) {
        super(FormulaVariableTypeModel.EXTERNAL_VARIABLE, name);
    }

    @Override
    public String toString() {
        return '#' + this.name;
    }
}
