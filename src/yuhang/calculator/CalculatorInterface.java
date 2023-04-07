package yuhang.calculator;

import java.math.BigDecimal;

public interface CalculatorInterface {
     double calculate(BigDecimal v1, BigDecimal v2, String sign);
     double redo();
     double undo();
}