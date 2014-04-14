package by.muna.moep.post.formula.types;

import by.muna.moep.post.formula.FormulaRuntimeException;

public interface IFormulaValue {
    FormulaValueType getValueType();

    default IFormulaValue add(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("add not implemented for " + this + " and " + o);
    }
    default IFormulaValue subtract(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("subtract not implemented for " + this + " and " + o);
    }
    default IFormulaValue multiply(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("multiply not implemented for " + this + " and " + o);
    }
    default IFormulaValue divide(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("divide not implemented for " + this + " and " + o);
    }
    default IFormulaValue pow(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("pow not implemented for " + this + " and " + o);
    }
    default IFormulaValue mod(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("mod not implemented for " + this + " and " + o);
    }
    default IFormulaValue xor(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("xor not implemented for " + this + " and " + o);
    }
    default FormulaBooleanValue isEqual(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("isEqual not implemented for " + this + " and " + o);
    }
    default FormulaBooleanValue lt(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("lt not implemented for " + this + " and " + o);
    }
    default FormulaBooleanValue lte(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("lte not implemented for " + this + " and " + o);
    }
    default FormulaBooleanValue gt(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("gt not implemented for " + this + " and " + o);
    }
    default FormulaBooleanValue gte(IFormulaValue o) throws FormulaRuntimeException {
        throw new FormulaRuntimeException("gte not implemented for " + this + " and " + o);
    }
}