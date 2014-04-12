package by.muna.moep.post.formula.tests;

import by.muna.moep.post.formula.FormulaException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.IFormulaBuilder;
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
            "$a = true; if $a then 2 else 7", Collections.emptyMap(),
            2
        );

        this.testIntExpression(
            "$a = false; $b = (if $a then false else true); if $b then 7 else 9",
            Collections.emptyMap(),
            7
        );
    }

    @Test
    public void switchTest() throws FormulaException {
        IFormulaParser parser = FormulaParserFactory.createFormulaParser();

        parser.feed(
            "switch $a: case 'dog': 7, case 'cat': 5," +
            "case 'unicorn': 42, case 'wolf': 0 end"
        );

        IFormulaBuilder formulaBuilder = parser.end();

        IFormula formula = formulaBuilder.buildFormula();

        Map<String, IFormulaValue> args = new HashMap<>();
        args.put("a", new FormulaStringValue("unicorn"));

        IFormulaValue result = formula.eval(args);

        Assert.assertEquals(FormulaValueType.INT, result.getValueType());
        Assert.assertEquals(42, ((FormulaIntValue) result).getValue());
    }

    private void testIntExpression(
        String expr, Map<String, IFormulaValue> args, int expected) throws FormulaException
    {
        IFormulaParser parser = FormulaParserFactory.createFormulaParser();

        parser.feed(expr);

        IFormulaBuilder formulaBuilder = parser.end();

        IFormula formula = formulaBuilder.buildFormula();

        IFormulaValue result = formula.eval(args);

        Assert.assertEquals(FormulaValueType.INT, result.getValueType());
        Assert.assertEquals(expected, ((FormulaIntValue) result).getValue());
    }
}
