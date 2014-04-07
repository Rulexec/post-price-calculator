package by.muna.moep.post.formula.tests;

import by.muna.moep.post.formula.BadFormulaException;
import by.muna.moep.post.formula.FormulaRuntimeException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.builtin.SqrtFormula;
import by.muna.moep.post.formula.parser.BadFormulaSyntaxException;
import by.muna.moep.post.formula.parser.IFormulaParser;
import by.muna.moep.post.formula.types.FormulaFloatValue;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.FormulaValueType;
import by.muna.moep.post.formula.types.IFormulaValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BasicFormulaTest {
    private IFormulaParser createFormulaParser() {
        // FIXME: Когда будет реализация парсера, инстанцировать его класс здесь
        return null;
    }

    @Test
    public void euclideNormTest()
        throws BadFormulaSyntaxException, BadFormulaException, FormulaRuntimeException
    {
        IFormulaParser parser = this.createFormulaParser();

        parser.feed("sqrt($x^2 + $y^2)");

        IFormulaBuilder formulaBuilder = parser.end();

        Set<String> requiredBuiltins = formulaBuilder.requiredBuiltin();
        Set<String> requiredArgs = formulaBuilder.requiredArgs();

        Assert.assertEquals(0, formulaBuilder.requiredExternals().size());
        Assert.assertEquals(1, requiredBuiltins.size());
        Assert.assertEquals(2, requiredArgs.size());

        Assert.assertTrue(requiredBuiltins.contains("sqrt"));
        Assert.assertTrue(requiredArgs.contains("x"));
        Assert.assertTrue(requiredArgs.contains("y"));

        formulaBuilder.putBuiltin("sqrt", SqrtFormula.SQRT_FUNCTION);

        IFormula formula = formulaBuilder.buildFormula();

        Map<String, IFormulaValue> args = new HashMap<>(2);
        args.put("x", new FormulaFloatValue(3.14));
        args.put("y", new FormulaIntValue(42));

        IFormulaValue result = formula.eval(args);

        Assert.assertEquals(FormulaValueType.FLOAT, result.getValueType());

        double r = ((FormulaFloatValue) result).getValue();

        Assert.assertEquals(42.1172126333165, r, 0.0000005);
    }
}
