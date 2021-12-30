package me.siyoon.stockfilter.domain.performance;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PSR {

    public static final PSR UNKNOWN_VALUE = new PSR(Double.MAX_VALUE);

    private final Double value;

    public static PSR psr(Double marketCap, SalesRevenue... salesRevenues) {
        double value = marketCap / SalesRevenue.totalValue(salesRevenues);
        return new PSR(value);
    }

    public Double inverseValue() {
        return 1 / value;
    }
}
