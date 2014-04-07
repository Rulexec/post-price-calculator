package by.muna.moep.post.formula;

import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public interface IFormulaBuilder {
    Set<String> requiredArgs();
    Set<String> requiredExternals();
    Set<String> requiredBuiltin();

    void putArg(String name, IFormulaValue value);
    void putExternal(String name, IFormulaValue value);
    void putBuiltin(String name, IFormulaValue value);

    IFormula buildFormula() throws BadFormulaException;

    default void putAllArgs(Map<String, IFormulaValue> args) {
        for (Entry<String, IFormulaValue> entry : args.entrySet()) {
            this.putArg(entry.getKey(), entry.getValue());
        }
    }
    default void putAllExternals(Map<String, IFormulaValue> externals) {
        for (Entry<String, IFormulaValue> entry : externals.entrySet()) {
            this.putExternal(entry.getKey(), entry.getValue());
        }
    }
    default void putAllBuiltin(Map<String, IFormulaValue> builtins) {
        for (Entry<String, IFormulaValue> entry : builtins.entrySet()) {
            this.putBuiltin(entry.getKey(), entry.getValue());
        }
    }
}
