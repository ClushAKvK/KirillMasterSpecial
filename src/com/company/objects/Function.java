package com.company.objects;

import javacalculus.core.CALC;
import javacalculus.core.CalcParser;
import javacalculus.evaluator.CalcSUB;
import javacalculus.exception.CalcSyntaxException;
import javacalculus.struct.CalcDouble;
import javacalculus.struct.CalcObject;
import javacalculus.struct.CalcSymbol;

public class Function {
    private String expression;

    private String variable = "x";

    public Function(String expression) {
        this.expression = expression;
        //expressT(express);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getValueIn(double valueX) {
        CalcObject result = parseFunction(expression);

        result = subst(result, "x", valueX);
        result = CALC.SYM_EVAL(result);

        return Double.parseDouble(String.valueOf(result));
    }

    public double getValueIn(double valueX, double valueY) {
        CalcObject result = parseFunction(expression);

        result = subst(result, "x", valueX);
        result = subst(result, "y", valueY);
        result = CALC.SYM_EVAL(result);

        return Double.parseDouble(String.valueOf(result));
    }


    public double getFirstDifferentiateIn(double valueX, double valueY, String variable) {
        String command = "DIFF(" + expression + ", " + variable + ")";
        CalcObject result = parseFunction(command);

        result = subst(result, "x", valueX);
        result = subst(result, "y", valueY);
        result = CALC.SYM_EVAL(result);

        return Double.parseDouble(String.valueOf(result));
    }


    private void expressT(String T) {
        expression = expression + " + " + T;
    }


    private CalcObject parseFunction(String command) {

        command = command.replace("sin", "SIN");
        command = command.replace("cos", "COS");
        command = command.replace("ln", "LN");

        CalcParser parser = new CalcParser();
        CalcObject parsed = null;
        try {
            parsed = parser.parse(command);
        } catch (CalcSyntaxException e) {
            e.printStackTrace();
        }

        CalcObject result = null;
        try {
            result = parsed.evaluate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private static CalcObject subst(CalcObject input, String var, double number)
    {
        CalcSymbol symbol = new CalcSymbol(var);
        CalcDouble value = new CalcDouble(number);
        return CalcSUB.numericSubstitute(input, symbol, value);
    }


}

