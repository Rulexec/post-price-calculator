package by.muna.moep.post.formula.tests;

import by.muna.moep.post.formula.FormulaException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.builtin.BuiltInFormulas;
import by.muna.moep.post.formula.parser.IFormulaParser;
import by.muna.moep.post.formula.tests.factories.FormulaParserFactory;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.FormulaStringValue;
import by.muna.moep.post.formula.types.FormulaValueType;
import by.muna.moep.post.formula.types.IFormulaValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstructionsTest {
    @Test
    public void ifTest() throws FormulaException {
        this.testIntExpression(
            "if (2 == 2) then 4 else 5", Collections.emptyMap(),
            4
        );

        this.testIntExpression(
            "$a = true; if $a then 2 else 7", new HashMap<>(),
            2
        );

        this.testIntExpression(
            "$a = false; $b = (if $a then false else true); if $b then 7 else 9",
            new HashMap<>(),
            7
        );
    }

    @Test
    public void switchTest() throws FormulaException {
        Map<String, IFormulaValue> args = new HashMap<>();
        args.put("a", new FormulaStringValue("unicorn"));

        IFormulaValue result = this.evalFormula(
            "switch $a: case 'dog': 7 case 'cat': 5\n" +
            "case 'unicorn': 42 case 'wolf': 0",
            args
        );

        Assert.assertEquals(FormulaValueType.INT, result.getValueType());
        Assert.assertEquals(42, ((FormulaIntValue) result).getValue());
    }

    @Test
    public void scopesTest() throws FormulaException {
        IFormulaValue result = this.evalFormula(
            "$a = 2; $b = 5;" +
            "$a = 8; { global $b; $a = 12; $b = 14 };" +
            "if ($a == 8 && $b == 14) then 42 else 0",
            Collections.emptyMap()
        );

        Assert.assertEquals(42, ((FormulaIntValue) result).getValue());
    }

    private void testIntExpression(
        String expr, Map<String, IFormulaValue> args, int expected) throws FormulaException
    {
        IFormulaValue result = this.evalFormula(expr, args);

        Assert.assertEquals(FormulaValueType.INT, result.getValueType());
        Assert.assertEquals(expected, ((FormulaIntValue) result).getValue());
    }

    private IFormulaValue evalFormula(String code, Map<String, IFormulaValue> args
        ) throws FormulaException
    {
        IFormulaParser parser = FormulaParserFactory.createFormulaParser();

        parser.feed(code);

        IFormulaBuilder formulaBuilder = parser.end();
        BuiltInFormulas.inject(formulaBuilder);

        IFormula formula = formulaBuilder.buildFormula();

        return formula.eval(args);
    }
}
