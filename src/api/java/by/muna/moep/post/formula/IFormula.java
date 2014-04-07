package by.muna.moep.post.formula;

import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.Map;

public interface IFormula {
    IFormulaValue eval(Map<String, IFormulaValue> args) throws FormulaRuntimeException;
}
