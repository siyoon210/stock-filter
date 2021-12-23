package me.siyoon.stockfilter.domain.financial;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CFO { // 영업활동현금흐름 (Cash Flows from Operating)

    public static final CFO UNKNOWN_VALUE = new CFO(Double.MIN_VALUE);

    private final Double value; // 억원
}
