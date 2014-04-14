package by.muna.moep.post.formula;

import by.muna.moep.post.formula.model.IFormulaExpressionModel;
import by.muna.moep.post.formula.model.expressions.FormulaAssignmentModel;
import by.muna.moep.post.formula.model.expressions.FormulaBuiltInOperationModel;
import by.muna.moep.post.formula.model.expressions.FormulaExpressionsModel;
import by.muna.moep.post.formula.model.expressions.FormulaFunctionCallModel;
import by.muna.moep.post.formula.model.expressions.FormulaIfModel;
import by.muna.moep.post.formula.model.expressions.FormulaIntModel;
import by.muna.moep.post.formula.model.expressions.FormulaStringModel;
import by.muna.moep.post.formula.model.expressions.FormulaSwitchModel;
import by.muna.moep.post.formula.model.expressions.cases.FormulaSwitchCaseModel;
import by.muna.moep.post.formula.model.expressions.variables.FormulaVariableModel;
import by.muna.moep.post.formula.types.FormulaBooleanValue;
import by.muna.moep.post.formula.types.FormulaFunctionValue;
import by.muna.moep.post.formula.types.FormulaIntValue;
import by.muna.moep.post.formula.types.FormulaStringValue;
import by.muna.moep.post.formula.types.FormulaValueType;
import by.muna.moep.post.formula.types.IFormulaValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Formula implements IFormula {
    private IFormulaExpressionModel expr;
    private Map<String, IFormulaValue> args, builtins, externals;

    public Formula(
        IFormulaExpressionModel expr,
        Map<String, IFormulaValue> args,
        Map<String, IFormulaValue> builtins,
        Map<String, IFormulaValue> externals)
    {
        this.expr = expr;
        this.args = args;
        this.builtins = builtins;
        this.externals = externals;
    }

    @Override
    public IFormulaValue eval(Map<String, IFormulaValue> args) throws FormulaRuntimeException {
        return this.eval(this.expr, args);
    }

    private IFormulaValue eval(
        IFormulaExpressionModel expr,
        Map<String, IFormulaValue> args) throws FormulaRuntimeException
    {
        if (expr == null) return null;

        switch (expr.getExpressionType()) {
        case EXPRESSIONS: {
            Iterator<IFormulaExpressionModel> it =
                ((FormulaExpressionsModel) expr).getExpressions().iterator();

            IFormulaValue value = null;

            while (true) {
                if (!it.hasNext()) return value;

                value = this.eval(it.next(), args);
            }
        }
        case IF: {
            FormulaIfModel ifModel = (FormulaIfModel) expr;

            IFormulaValue value = this.eval(ifModel.getCondition(), args);

            if (value.getValueType() == FormulaValueType.BOOLEAN) {
                return ((FormulaBooleanValue) value).getValue() ?
                    this.eval(ifModel.getThenExpression(), args) :
                    (ifModel.getElseExpression() != null ?
                        this.eval(ifModel.getElseExpression(), args) :
                        null);
            } else {
                throw new FormulaRuntimeException("if condition is not boolean");
            }
        }
        case SWITCH: {
            FormulaSwitchModel switchModel = (FormulaSwitchModel) expr;

            IFormulaValue value = this.eval(switchModel.getExpression(), args);

            for (FormulaSwitchCaseModel caseModel : switchModel.getCases()) {
                IFormulaValue caseValue = this.eval(caseModel.getMatchingExpression(), args);

                if (value.isEqual(caseValue).getValue()) {
                    return this.eval(caseModel.getResultExpression(), args);
                }
            }

            if (switchModel.getDefaultCase() != null) {
                return this.eval(switchModel.getDefaultCase(), args);
            } else {
                throw new FormulaRuntimeException("no case to: " + value);
            }
        }
        case ASSIGNMENT: {
            FormulaAssignmentModel assignmentModel = (FormulaAssignmentModel) expr;

            IFormulaValue value = this.eval(assignmentModel.getExpression(), args);

            args.put(assignmentModel.getVariable().getName(), value);

            return value;
        }
        case FUNCTION_CALL: {
            FormulaFunctionCallModel functionCallModel = (FormulaFunctionCallModel) expr;

            IFormulaValue value = this.eval(functionCallModel.getFunction(), args);

            if (value.getValueType() == FormulaValueType.FUNCTION) {
                FormulaFunctionValue fn = (FormulaFunctionValue) value;

                Map<String, IFormulaValue> arguments = new HashMap<>();

                int i = 0;

                for (IFormulaExpressionModel arg : functionCallModel.getArgs()) {
                    arguments.put(Integer.toString(i), this.eval(arg, args));

                    i++;
                }

                return fn.getValue().eval(arguments);
            } else {
                throw new FormulaRuntimeException("trying to call " + value);
            }
        }
        case BUILTIN_OPERATION: {
            FormulaBuiltInOperationModel builtInModel =
                (FormulaBuiltInOperationModel) expr;

            List<IFormulaValue> arguments = new ArrayList<>(builtInModel.getArgs().size());

            for (IFormulaExpressionModel e : builtInModel.getArgs()) {
                arguments.add(this.eval(e, args));
            }

            return builtInModel.getBuiltInType().eval(arguments);
        }
        case VARIABLE: {
            FormulaVariableModel variableModel = (FormulaVariableModel) expr;

            switch (variableModel.getVariableType()) {
            case USER_VARIABLE: {
                IFormulaValue value = this.args.get(variableModel.getName());
                if (value == null) value = args.get(variableModel.getName());

                return value;
            }
            case BUILTIN_VARIABLE:
                return this.builtins.get(variableModel.getName());
            case EXTERNAL_VARIABLE:
                return this.externals.get(variableModel.getName());
            default:
                throw new RuntimeException("Impossible: " + variableModel.getVariableType());
            }
        }
        case INT: return new FormulaIntValue(((FormulaIntModel) expr).getValue());
        case STRING: return new FormulaStringValue(((FormulaStringModel) expr).getValue());
        default: throw new RuntimeException("Impossible: " + expr.getExpressionType());
        }
    }
}
