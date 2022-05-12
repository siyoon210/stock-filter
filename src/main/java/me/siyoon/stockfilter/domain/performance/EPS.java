package me.siyoon.stockfilter.domain.performance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class EPS implements Serializable { // 주당순이익 (Earning Per Share)

    public static final EPS UNKNOWN_VALUE = new EPS(Double.MIN_VALUE);

    private final Double value;

    public static EPS from(Double value) {
        if (value == null) {
            return UNKNOWN_VALUE;
        }
        return new EPS(value);
    }

    public PER calculatedPer(Double price) {
        if (this.equals(UNKNOWN_VALUE) || price == null) {
            return PER.UNKNOWN_VALUE;
        }
        return PER.from(price / value);
    }

    public Double increaseRate(EPS targetEps) {
        if (this.value == 0.0) {
            return Double.MIN_VALUE;
        }

        return BigDecimal.valueOf(targetEps.value - this.value)
                         .divide(BigDecimal.valueOf(Math.abs(this.value)), 4, RoundingMode.HALF_UP)
                         .multiply(BigDecimal.valueOf(100L))
                         .doubleValue();
    }

    public boolean isGreaterThan(Double value) {
        return this.value > value;
    }

    public boolean isNegative() {
        return value < 0;
    }

    public boolean isZero() {
        return value == 0;
    }

    public boolean isPositive() {
        return value > 0;
    }

    public static Double totalValue(EPS... epsArray) {
        return Arrays.stream(epsArray)
                     .map(eps -> eps.value)
                     .reduce(0.0, Double::sum);
    }

    public EPS increase(Double rate) {
        try {
            double increaseValue = BigDecimal.valueOf(value)
                                             .multiply(BigDecimal.valueOf(1 + (rate / 100)))
                                             .doubleValue();
            return EPS.from(increaseValue);
        } catch (Exception e) {
            log.warn("EPS increase error. {} rate: {}", this, rate);
            return EPS.UNKNOWN_VALUE;
        }
    }
}
