package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class DPS implements Serializable { // 주당 배당금 (Dividend Per Share)

    public static final DPS UNKNOWN_VALUE = new DPS(Double.MIN_VALUE);

    private final Double value;

    public static DPS from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }

        return new DPS(value);
    }

    public DividendYield expectedDividendYield(Double price) { // 예상 배당률
        if (price == null) {
            return DividendYield.UNKNOWN_VALUE;
        }
        double calculatedValue = BigDecimal.valueOf(value)
                                           .divide(BigDecimal.valueOf(price), 2,
                                                   RoundingMode.HALF_UP)
                                           .multiply(BigDecimal.valueOf(100))
                                           .doubleValue();
        return DividendYield.from(calculatedValue);
    }
}
