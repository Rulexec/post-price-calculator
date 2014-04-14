package by.muna.moep.post.formula.parser;

import by.muna.moep.post.formula.BadFormulaException;
import by.muna.moep.post.formula.Formula;
import by.muna.moep.post.formula.IFormula;
import by.muna.moep.post.formula.IFormulaBuilder;
import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.moep.post.formula.model.expressions.FormulaAssignmentModel;
import by.muna.moep.post.formula.model.expressions.FormulaBuiltInOperationModel;
import by.muna.moep.post.formula.model.expressions.FormulaExpressionsModel;
import by.muna.moep.post.formula.model.expressions.FormulaFunctionCallModel;
import by.muna.moep.post.formula.model.expressions.FormulaIfModel;
import by.muna.moep.post.formula.model.expressions.FormulaSwitchModel;
import by.muna.moep.post.formula.model.expressions.cases.FormulaSwitchCaseModel;
import by.muna.moep.post.formula.model.expressions.variables.FormulaVariableModel;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FormulaBuilder implements IFormulaBuilder {
    private IFormulaExpressionModel expression;

    private Set<String> argNames = new HashSet<>();
    private Set<String> externalNames = new HashSet<>();
    private Set<String> builtinNames = new HashSet<>();

    private Map<String, IFormulaValue> args = new HashMap<>();
    private Map<String, IFormulaValue> externals = new HashMap<>();
    private Map<String, IFormulaValue> builtins = new HashMap<>();

    public FormulaBuilder(IFormulaExpressionModel expression) {
        this.expression = expression;

        this.registerNames(this.expression);
    }

    private void registerNames(IFormulaExpressionModel expr) {
        if (expr == null) return;

        switch (expr.getExpressionType()) {
        case EXPRESSIONS:
            ((FormulaExpressionsModel) expr).getExpressions().forEach(this::registerNames);
            break;
        case IF: {
            FormulaIfModel ifModel = (FormulaIfModel) expr;

            this.registerNames(ifModel.getCondition());
            this.registerNames(ifModel.getThenExpression());
            this.registerNames(ifModel.getElseExpression());
        } break;
        case SWITCH: {
            FormulaSwitchModel switchModel = (FormulaSwitchModel) expr;

            this.registerNames(switchModel.getExpression());
            this.registerNames(switchModel.getDefaultCase());

            for (FormulaSwitchCaseModel caseMode : switchModel.getCases()) {
                this.registerNames(caseMode.getMatchingExpression());
                this.registerNames(caseMode.getResultExpression());
            }
        } break;
        case ASSIGNMENT:
            this.registerNames(((FormulaAssignmentModel) expr).getExpression());
            break;
        case FUNCTION_CALL: {
            FormulaFunctionCallModel functionCallModel = (FormulaFunctionCallModel) expr;

            this.registerNames(functionCallModel.getFunction());
            for (IFormulaExpressionModel e : functionCallModel.getArgs()) {
                this.registerNames(e);
            }
        } break;
        case BUILTIN_OPERATION:
            ((FormulaBuiltInOperationModel) expr).getArgs().forEach(this::registerNames);
            break;
        case VARIABLE: {
            FormulaVariableModel variableModel = (FormulaVariableModel) expr;

            switch (variableModel.getVariableType()) {
            case USER_VARIABLE:
                this.argNames.add(variableModel.getName());
                break;
            case BUILTIN_VARIABLE:
                this.builtinNames.add(variableModel.getName());
                break;
            case EXTERNAL_VARIABLE:
                this.externalNames.add(variableModel.getName());
                break;
            default:
                throw new RuntimeException("Impossible: " + variableModel.getVariableType());
            }
        } break;
        case INT: break;
        case STRING: break;
        default: throw new RuntimeException("Impossible: " + expr.getExpressionType());
        }
    }

    @Override
    public IFormula buildFormula() throws BadFormulaException {
        if (!this.builtinNames.isEmpty() || !this.externalNames.isEmpty()) {
            throw new BadFormulaException(
                "Formula is not fullfilled: " +
                this.builtinNames.toString() + ", #" + this.externalNames.toString()
            );
        }

        return new Formula(this.expression, this.args, this.builtins, this.externals);
    }

    @Override
    public Set<String> requiredArgs() {
        return this.argNames;
    }

    @Override
    public Set<String> requiredExternals() {
        return this.externalNames;
    }

    @Override
    public Set<String> requiredBuiltin() {
        return this.builtinNames;
    }

    @Override
    public void putArg(String name, IFormulaValue value) {
        this.argNames.remove(name);
        this.args.put(name, value);
    }

    @Override
    public void putExternal(String name, IFormulaValue value) {
        this.externalNames.remove(name);
        this.externals.put(name, value);
    }

    @Override
    public void putBuiltin(String name, IFormulaValue value) {
        this.builtinNames.remove(name);
        this.builtins.put(name, value);
    }
}
