package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GPA { // GP/A = 매출총이익(GrossProfitMargin) / 자산(Asset)

    public static final GPA UNKNOWN_VALUE = new GPA(Double.MIN_VALUE);

    private final Double value;
}
