package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PCR implements Serializable {

    public static final PCR UNKNOWN_VALUE = new PCR(Double.MAX_VALUE);

    private final Double value;

    public static PCR pcr(Double marketCap, CFO... cfos) {
        double value = marketCap / CFO.totalValue(cfos);
        return new PCR(value);
    }

    public Double inverseValue() {
        return 1 / value;
    }
}
