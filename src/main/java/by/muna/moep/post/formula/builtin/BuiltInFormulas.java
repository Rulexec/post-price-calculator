package by.muna.moep.post.formula.builtin;

import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.builtin.rounding.CeilFormula;
import by.muna.moep.post.formula.builtin.rounding.FloorFormula;
import by.muna.moep.post.formula.builtin.rounding.RoundFormula;
import by.muna.moep.post.formula.types.FormulaBooleanValue;
import by.muna.moep.post.formula.types.FormulaFunctionValue;

public class BuiltInFormulas {
    public static void inject(IFormulaBuilder builder) {
        builder.putBuiltin("true", FormulaBooleanValue.TRUE);
        builder.putBuiltin("false", FormulaBooleanValue.TRUE);

        builder.putBuiltin("floor", new FormulaFunctionValue(new FloorFormula()));
        builder.putBuiltin("ceil", new FormulaFunctionValue(new CeilFormula()));
        builder.putBuiltin("round", new FormulaFunctionValue(new RoundFormula()));

        builder.putBuiltin("sqrt", SqrtFormula.SQRT_FUNCTION);
    }
}