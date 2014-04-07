package by.muna.moep.post.formula.parser;

import by.muna.moep.post.formula.IFormulaBuilder;

public interface IFormulaParser {
    void feed(CharSequence chars) throws BadFormulaSyntaxException;
    IFormulaBuilder end() throws BadFormulaSyntaxException;
}
