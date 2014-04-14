package by.muna.moep.post.formula.builtin;

import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.types.FormulaBooleanValue;

public class BuiltInFormulas {
    public static void inject(IFormulaBuilder builder) {
        builder.putBuiltin("true", FormulaBooleanValue.TRUE);
        builder.putBuiltin("false", FormulaBooleanValue.TRUE);

        builder.putBuiltin("sqrt", SqrtFormula.SQRT_FUNCTION);
    }
}