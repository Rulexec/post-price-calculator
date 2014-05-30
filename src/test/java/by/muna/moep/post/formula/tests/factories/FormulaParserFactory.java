package by.muna.moep.post.formula.tests.factories;

import by.muna.moep.post.formula.parser.FormulaParser;
import by.muna.moep.post.formula.parser.IFormulaParser;

public class FormulaParserFactory {
    public static IFormulaParser createFormulaParser() {
        return new FormulaParser();
    }
}
