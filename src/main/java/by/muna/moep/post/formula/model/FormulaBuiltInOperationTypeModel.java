package by.muna.moep.post.formula.model;

import by.muna.moep.post.formula.FormulaRuntimeException;
import by.muna.moep.post.formula.types.FormulaBooleanValue;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FormulaBuiltInOperationTypeModel {
    ADD("+", (a, b) -> a.add(b)),
    SUBTRACT("-", (a, b) -> a.subtract(b)),
    MULTIPLY("*", (a, b) -> a.multiply(b)),
    DIVIDE("/", (a, b) -> a.divide(b)),
    POW("^", (a, b) -> a.pow(b)),
    MOD("%", (a, b) -> a.mod(b)),
    LOGICAL_AND("&&", (a, b) ->
        new FormulaBooleanValue(
            ((FormulaBooleanValue) a).getValue() && ((FormulaBooleanValue) b).getValue()
        )),
    LOGICAL_OR("||", (a, b) ->
        new FormulaBooleanValue(
            ((FormulaBooleanValue) a).getValue() || ((FormulaBooleanValue) b).getValue()
        )),
    EQUALS("==", (a, b) -> a.isEqual(b)),
    XOR("xor", (a, b) -> a.xor(b)),
    LESS("<", (a, b) -> a.lt(b)),
    LESS_OR_EQUAL("<=", (a, b) -> a.lte(b)),
    GREATER(">", (a, b) -> a.gt(b)),
    GREATER_OR_EQUAL(">=", (a, b) -> a.gte(b));

    @FunctionalInterface
    private static interface BuiltInFunction {
        IFormulaValue eval(IFormulaValue a, IFormulaValue b) throws FormulaRuntimeException;
    }

    private static Map<String, FormulaBuiltInOperationTypeModel> stringToType = new HashMap<>();
    static {
        for (FormulaBuiltInOperationTypeModel t : FormulaBuiltInOperationTypeModel.values()) {
            FormulaBuiltInOperationTypeModel.stringToType.put(t.getString(), t);
        }
    }

    private String s;
    private BuiltInFunction fn;
    private FormulaBuiltInOperationTypeModel(String s, BuiltInFunction fn) {
        this.s = s;
        this.fn = fn;
    }

    public IFormulaValue eval(List<IFormulaValue> args) throws FormulaRuntimeException {
        if (args.size() != 2) {
            throw new FormulaRuntimeException("builtin operations accepts only 2 args");
        }

        try {
            return this.fn.eval(args.get(0), args.get(1));
        } catch (FormulaRuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new FormulaRuntimeException(ex);
        }
    }

    public String getString() {
        return this.s;
    }

    public static FormulaBuiltInOperationTypeModel fromString(String s) {
        return FormulaBuiltInOperationTypeModel.stringToType.get(s);
    }
}
