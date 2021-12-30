package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@Builder
@AllArgsConstructor
@ToString
public class FnGuideFinanceTableExtractParam { // FnGuide 메인페이지 Financial Highlight 테이블 파싱용
    public final String label;
    public final int elementIndex;
}
