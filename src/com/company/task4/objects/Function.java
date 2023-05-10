package com.company.task4.objects;

import javacalculus.core.CALC;
import javacalculus.core.CalcParser;
import javacalculus.evaluator.CalcSUB;
import javacalculus.exception.CalcSyntaxException;
import javacalculus.struct.CalcDouble;
import javacalculus.struct.CalcObject;
import javacalculus.struct.CalcSymbol;

import java.util.List;

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

        result = subst(result, "t", valueX);
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

    public double getValueIn(double valueT, double [] valueY) {
        CalcObject result = parseFunction(expression);

        if (expression.contains("t"))
            result = subst(result, "t", valueT);
        if (expression.contains("x"))
            result = subst(result, "x", valueY[0]);
        if (expression.contains("y"))
            result = subst(result, "y", valueY[1]);
        if (valueY.length == 3)
            result = subst(result, "z", valueY[2]);
        result = CALC.SYM_EVAL(result);

        return Double.parseDouble(String.valueOf(result));
    }

    public double getValueIn(double valueT, List<Double> valueY) {
        CalcObject result = parseFunction(expression);

        if (expression.contains("t"))
            result = subst(result, "t", valueT);
        if (expression.contains("x"))
            result = subst(result, "x", valueY.get(0));
        if (expression.contains("y"))
            result = subst(result, "y", valueY.get(1));
        if (valueY.size() == 3)
            result = subst(result, "z", valueY.get(2));
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

