package by.muna.moep.post;

import by.muna.moep.post.data.IPostParameter;
import by.muna.moep.post.data.IPostType;
import by.muna.moep.post.data.IPostTypeFull;
import by.muna.moep.post.database.IPostDatabase;
import by.muna.moep.post.database.PostDatabaseException;
import by.muna.moep.post.database.models.IDBPostType;
import by.muna.moep.post.formula.BadFormulaException;
import by.muna.moep.post.formula.FormulaRuntimeException;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.builtin.BuiltInFormulas;
import by.muna.moep.post.formula.parser.BadFormulaSyntaxException;
import by.muna.moep.post.formula.parser.IFormulaParser;
import by.muna.moep.post.formula.parser.IFormulaParserFactory;
import by.muna.moep.post.formula.types.FormulaFloatValue;
import by.muna.moep.post.formula.types.FormulaFunctionValue;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.FormulaStringValue;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Post implements IPost {
    private IPostDatabase db;
    private IFormulaParserFactory formulaParserFactory;

    public Post(IPostDatabase db, IFormulaParserFactory formulaParser) {
        this.db = db;
        this.formulaParserFactory = formulaParser;
    }

    @Override
    public List<IPostType> getPostTypes() throws PostException {
        try {
            List<IDBPostType> dbTypes = this.db.getAllTypes();

            return new ArrayList<>(dbTypes);
        } catch (PostDatabaseException ex) {
            throw new PostException(ex);
        }
    }

    @Override
    public IPostTypeFull getPostType(int id) throws PostException {
        try {
            return this.db.getPostType(id, true);
        } catch (PostDatabaseException ex) {
            throw new PostException(ex);
        }
    }

    @Override
    public long calculate(int postTypeId, Map<String, String> arguments) throws PostException {
        IDBPostType type;
        String formulaCode;

        try {
            type = this.db.getPostType(postTypeId, true);
            formulaCode = type.getFormula().getFormulaCode();
        } catch (PostDatabaseException ex) {
            throw new PostException(ex);
        }

        IFormula formula = this.createFormula(formulaCode);

        Map<String, IFormulaValue> args = new HashMap<>();

        for (IPostParameter parameter : type.getParameters()) {
            String value = arguments.get(parameter.getSlug());

            if (value == null) throw new PostException("No value of: " + parameter.getSlug());

            IFormulaValue formulaValue;

            switch (parameter.getData().getParameterType()) {
            case SWITCH: {
                if (value.length() >= 2) {
                    formulaValue = new FormulaStringValue(value.substring(1, value.length() - 1));
                } else {
                    throw new PostException("switch");
                }
            } break;
            case INT:
                formulaValue = new FormulaIntValue(Integer.parseInt(value));
                break;
            case UINT: {
                int v = Integer.parseInt(value);
                if (v < 0) throw new PostException("uint");
                formulaValue = new FormulaIntValue(v);
            } break;
            case FLOAT:
                formulaValue = new FormulaFloatValue(Double.parseDouble(value));
                break;
            case UFLOAT: {
                double d = Double.parseDouble(value);
                if (d < 0) throw new PostException("ufloat");
                formulaValue = new FormulaFloatValue(d);
            } break;
            default: throw new PostException("Impossible: " + parameter.getData().getParameterType());
            }

            args.put(parameter.getSlug(), formulaValue);
        }

        try {
            IFormulaValue result = formula.eval(args);
            if (result instanceof FormulaIntValue) {
                return ((FormulaIntValue) result).getValue();
            } else {
                throw new PostException("Result must be int, but: " + result.getClass());
            }
        } catch (FormulaRuntimeException ex) {
            throw new PostException(ex);
        }
    }

    private IFormula createFormula(String code) throws PostException {
        return this.createFormula(code, new HashMap<>());
    }
    private IFormula createFormula(String code, Map<String, IFormulaValue> formulas)
        throws PostException
    {
        IFormulaBuilder formulaBuilder;

        try {
            IFormulaParser parser = this.formulaParserFactory.createParser();
            parser.feed(code);
            formulaBuilder = parser.end();
        } catch (BadFormulaSyntaxException ex) {
            throw new PostException(ex);
        }

        BuiltInFormulas.inject(formulaBuilder);

        for (String formulaSlug : formulaBuilder.requiredExternals()) {
            IFormulaValue formulaValue = formulas.get(formulaSlug);

            if (formulaValue != null) {
                formulaBuilder.putExternal(formulaSlug, formulaValue);
            } else {
                String formulaCode;
                try {
                    formulaCode = this.db.getFormula(formulaSlug).getFormulaCode();
                } catch (PostDatabaseException ex) {
                    throw new PostException(ex);
                }

                IFormula formula = this.createFormula(formulaCode, formulas);
                IFormulaValue value = new FormulaFunctionValue(formula);

                formulas.put(formulaSlug, value);

                formulaBuilder.putExternal(formulaSlug, value);
            }
        }

        try {
            return formulaBuilder.buildFormula();
        } catch (BadFormulaException ex) {
            throw new PostException(ex);
        }
    }
}
