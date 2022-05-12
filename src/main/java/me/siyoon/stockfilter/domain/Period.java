package me.siyoon.stockfilter.domain;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public enum Period {
    FIVE_YEARS_AGO("5년전"),
    FOUR_YEARS_AGO("4년전"),
    THREE_YEARS_AGO("3년전"),
    TWO_YEARS_AGO("2년전"),
    LAST_YEAR("작년"),
    THIS_YEAR_EXPECTED("올해 예상"),
    NEXT_YEAR_EXPECTED("내년 예상"),

    FOUR_QUARTERS_AGO("4분기전"),
    THREE_QUARTERS_AGO("3분기전"),
    TWO_QUARTERS_AGO("2분기전"),
    LAST_QUARTER("직전 분기"),
    THIS_QUARTER_EXPECTED("이번 분기 예상"),
    NEXT_QUARTER_EXPECTED("다음 분기 예상"),
    AFTER_TWO_QUARTERS_EXPECTED("다음 2번째 분기 예상");

    public static final Set<Period> QUARTERS = new LinkedHashSet<>(
            List.of(FOUR_QUARTERS_AGO, THREE_QUARTERS_AGO,
                    TWO_QUARTERS_AGO, LAST_QUARTER,
                    THIS_QUARTER_EXPECTED,
                    NEXT_QUARTER_EXPECTED,
                    AFTER_TWO_QUARTERS_EXPECTED)
    );

    public static final Set<Period> YEARS = new LinkedHashSet<>(
            List.of(FIVE_YEARS_AGO, FOUR_YEARS_AGO, THREE_YEARS_AGO, TWO_YEARS_AGO, LAST_YEAR, THIS_YEAR_EXPECTED,
                    NEXT_YEAR_EXPECTED));

    public final String description;

    Period(String description) {
        this.description = description;
    }
}
