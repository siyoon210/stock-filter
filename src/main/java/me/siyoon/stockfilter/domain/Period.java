package me.siyoon.stockfilter.domain;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public enum Period {
    THREE_YEARS_AGO("3년전"),
    TWO_YEARS_AGO("2년전"),
    LAST_YEAR("작년"),
    THIS_YEAR("올해"),
    FOUR_QUARTERS_AGO("4분기전"),
    THREE_QUARTERS_AGO("3분기전"),
    TWO_QUARTERS_AGO("2분기전"),
    LAST_QUARTER("직전 분기"),
    NEXT_QUARTER_EXPECTED_INDEX("직후 분기 예상"),
    AFTER_TWO_QUARTERS_EXPECTED_INDEX("다음 2번째 분기 예상"),
    AFTER_THREE_QUARTERS_EXPECTED_INDEX("다음 3번쨰 분기 예상");

    public static final Set<Period> QUARTERS = new LinkedHashSet<>(
            List.of(FOUR_QUARTERS_AGO, THREE_QUARTERS_AGO,
                    TWO_QUARTERS_AGO, LAST_QUARTER,
                    NEXT_QUARTER_EXPECTED_INDEX,
                    AFTER_TWO_QUARTERS_EXPECTED_INDEX,
                    AFTER_THREE_QUARTERS_EXPECTED_INDEX));

    public final String description;

    Period(String description) {
        this.description = description;
    }
}
