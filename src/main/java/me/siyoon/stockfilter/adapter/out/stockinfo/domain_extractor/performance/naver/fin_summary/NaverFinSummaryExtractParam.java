package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.fin_summary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class NaverFinSummaryExtractParam { // Naver 종목분석 - 기업현황 - Financial Summary 테이블 파싱용

    public final String expectedLabel;
    public final int elementIndex;
}
