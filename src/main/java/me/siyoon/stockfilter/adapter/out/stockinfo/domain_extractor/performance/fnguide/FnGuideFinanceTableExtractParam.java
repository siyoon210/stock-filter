package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.fnguide;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class FnGuideFinanceTableExtractParam { // FnGuide 메인페이지 Financial Highlight 테이블 파싱용
    public final String label;
    public final int elementIndex;
}
