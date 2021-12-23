package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.siyoon.stockfilter.domain.financial.CFO;
import me.siyoon.stockfilter.domain.financial.Equity;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PCR {

    public static final PCR UNKNOWN_VALUE = new PCR(Double.MAX_VALUE);

    private final Double value;

    public static PCR pcr(Double marketCap, CFO... cfos) {
        double value = marketCap / CFO.totalValue(cfos);
        return new PCR(value);
    }
}
