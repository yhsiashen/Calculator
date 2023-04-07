package yuhang.calculator.impl;

import yuhang.calculator.CalculatorInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator implements CalculatorInterface {

    private final Stack<Double> undoStack;
    private final Stack<Double> redoStack;
    private static final int SCALE = 10;

    public Calculator() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * 根据运算符号计算，并返回结果
     */
    @Override
    public double calculate(BigDecimal v1, BigDecimal v2, String sign) {
        double result;
        try {
            switch (sign) {
                case "+":
                    result = v1.add(v2).doubleValue();
                    break;
                case "-":
                    result = v1.subtract(v2).doubleValue();
                    break;
                case "*":
                    result = v1.multiply(v2).doubleValue();
                    break;
                case "/":
                    result = v1.divide(v2, SCALE, RoundingMode.HALF_UP).doubleValue();
                    break;
                default:
                    throw new IllegalArgumentException("This sign: " + sign + " is not defined");
            }
            undoStack.push(result);
            return result;
        } catch (Exception e) {
            System.out.println("An exception has been occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 重做操作: 重做最近被撤销的操作并返回结果
     */
    @Override
    public double redo() {
        if (redoStack.isEmpty()) {
            throw new RuntimeException("No more recent deleted operations");
        }
        final double deletedValue = redoStack.pop();
        //push back into undoStack, it indicates that this operation is recovered
        undoStack.push(deletedValue);
        return deletedValue;
    }

    /**
     * 撤销操作: 取消最近的操作并返回被取消的结果
     */
    @Override
    public double undo() {
        if (undoStack.isEmpty()) {
            throw new RuntimeException("No more recent operations");
        }
        final double lastedValue = undoStack.pop();
        //push into redoStack for redo this operation
        redoStack.push(lastedValue);
        return lastedValue;
    }
}
