package me.siyoon.stockfilter.application.port.in;

import java.util.Arrays;
import java.util.List;

public enum Period {
    THREE_YEARS_AGO,
    TWO_YEARS_AGO,
    LAST_YEAR,
    THIS_YEAR,
    ;

    public static final List<Period> LAST_THREE_YEARS
            = Arrays.asList(LAST_YEAR, TWO_YEARS_AGO, THREE_YEARS_AGO);
}
