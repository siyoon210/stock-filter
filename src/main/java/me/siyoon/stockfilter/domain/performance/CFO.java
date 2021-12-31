package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class CFO implements Serializable { // 영업활동현금흐름 (Cash Flows from Operating)

    public static final CFO UNKNOWN_VALUE = new CFO(Double.MIN_VALUE);

    private final Double value; // 억원

    public static CFO from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }

        return new CFO(value);
    }

    public static Double totalValue(CFO... cfos) {
        return Arrays.stream(cfos)
                     .map(cfo -> cfo.value)
                     .reduce(0.0, Double::sum);
    }
}
