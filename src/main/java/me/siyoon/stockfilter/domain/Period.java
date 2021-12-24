package me.siyoon.stockfilter.domain;

import java.util.List;

public enum Period {
    THREE_YEARS_AGO("3년전"),
    TWO_YEARS_AGO("2년전"),
    LAST_YEAR("작년"),
    THIS_YEAR_EXPECTED("올해 예상"),
    NEXT_YEAR_EXPECTED("내년 예상"),

    FOUR_QUARTERS_AGO("4분기전"),
    THREE_QUARTERS_AGO("3분기전"),
    TWO_QUARTERS_AGO("2분기전"),
    LAST_QUARTER("직전 분기"),
    NEXT_QUARTER_EXPECTED("직후 분기 예상"),
    AFTER_TWO_QUARTERS_EXPECTED("다음 2번째 분기 예상"),
    AFTER_THREE_QUARTERS_EXPECTED("다음 3번쨰 분기 예상");

    public static final List<Period> QUARTERS =
            List.of(FOUR_QUARTERS_AGO, THREE_QUARTERS_AGO,
                    TWO_QUARTERS_AGO, LAST_QUARTER,
                    NEXT_QUARTER_EXPECTED,
                    AFTER_TWO_QUARTERS_EXPECTED,
                    AFTER_THREE_QUARTERS_EXPECTED);

    public static final List<Period> YEARS =
            List.of(THREE_YEARS_AGO, TWO_YEARS_AGO, LAST_YEAR, THIS_YEAR_EXPECTED,
                    NEXT_YEAR_EXPECTED);

    public final String description;

    Period(String description) {
        this.description = description;
    }
}
