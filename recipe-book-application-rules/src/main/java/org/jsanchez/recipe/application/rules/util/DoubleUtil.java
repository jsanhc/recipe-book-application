package org.jsanchez.recipe.application.rules.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface DoubleUtil {
    default Double addition(Double operator1, Double operator2) {
        return new BigDecimal(operator1 + operator2).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    default Double subtract(Double minuend, Double subtrahend) {
        return new BigDecimal(minuend - subtrahend).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
