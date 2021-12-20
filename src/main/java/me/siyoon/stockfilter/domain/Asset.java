package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Asset { // 자산

    public static final Asset UNKNOWN_VALUE = new Asset(Double.MIN_VALUE);

    private final Double value;
}
