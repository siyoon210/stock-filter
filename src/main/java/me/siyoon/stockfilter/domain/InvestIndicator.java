package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class InvestIndicator { // 투자 지표

    public final GPA gpa; //  GP/A = 매출총이익(GrossProfitMargin) / 자산(Asset)
    public final PER per;

}
