package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.siyoon.stockfilter.domain.financial.CFO;
import me.siyoon.stockfilter.domain.financial.SalesRevenue;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PSR {

    public static final PSR UNKNOWN_VALUE = new PSR(Double.MAX_VALUE);

    private final Double value;

    public static PSR pcr(Double marketCap, SalesRevenue... salesRevenues) {
        double value = marketCap / SalesRevenue.totalValue(salesRevenues);
        return new PSR(value);
    }
}
