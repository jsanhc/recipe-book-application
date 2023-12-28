package org.jsanchez.recipe.application.rules.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PrepareTimeUtil implements DoubleUtil {

    protected Double minutesToHours(Double operator) {
        return new BigDecimal(operator / 60.00).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    protected Double hoursToMinutes(Double operator) {
        return new BigDecimal(operator * 60.00).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
