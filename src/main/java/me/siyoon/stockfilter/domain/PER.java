package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PER {

    public static final PER UNKNOWN_VALUE = new PER(Double.MAX_VALUE);

    private final Double value;

    public boolean isLessThan(Double value) {
        if (isNegativePER()) {
            return false;
        }
        return this.value < value;
    }

    private boolean isNegativePER() {
        return this.value < 0;
    }
}
