package me.siyoon.stockfilter.domain.performance;

import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SalesRevenue { // 매출액

    public static final SalesRevenue UNKNOWN_VALUE = new SalesRevenue(Double.MIN_VALUE);

    private final Double value; // 억원

    public static SalesRevenue from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new SalesRevenue(value);
    }

    public static Double totalValue(SalesRevenue... salesRevenues) {
        return Arrays.stream(salesRevenues)
                     .map(salesRevenue -> salesRevenue.value)
                     .reduce(0.0, Double::sum);
    }
}
