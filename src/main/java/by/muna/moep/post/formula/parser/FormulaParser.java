package by.muna.moep.post.formula.parser;

import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.peg.PEGParser;
import by.muna.peg.grammar.exceptions.PEGParseException;
import by.muna.peg.interpreter.PEGInterpretativeParser;
import by.muna.peg.self.SelfParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FormulaParser implements IFormulaParser {
    private static PEGParser<IFormulaExpressionModel> pegParser = FormulaParser.createParser();

    private StringBuilder inputBuilder = new StringBuilder();

    public FormulaParser() {}

    @Override
    public void feed(CharSequence chars) throws BadFormulaSyntaxException {
        this.inputBuilder.append(chars);
    }

    @Override
    public IFormulaBuilder end() throws BadFormulaSyntaxException {
        IFormulaExpressionModel expr;

        try {
            expr = FormulaParser.pegParser.parse(
                this.inputBuilder.toString()
            );
        } catch (PEGParseException ex) {
            throw new BadFormulaSyntaxException(ex);
        }

        return new FormulaBuilder(expr);
    }

    private static PEGParser<IFormulaExpressionModel> createParser() {
        try {
            return new PEGInterpretativeParser<>(
                new SelfParser().parse(FormulaParser.loadTextFromResource(
                    "/parser/syntax/groovescript.syntax"
                )
            ), "start");
        } catch (PEGParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String loadTextFromResource(String path)
        throws RuntimeException
    {
        try {
            return new String(
                Files.readAllBytes(Paths.get(
                    FormulaParser.class.getResource(
                        path
                    ).toURI()
                )),
                StandardCharsets.UTF_8
            );
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }
}
