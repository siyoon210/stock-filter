package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance.naver.company_state;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class NaverCompanyStateTableExtractParam { // 네이버 메인페이지 기업실적 테이블 파싱용
    public final String label;
    public final int elementIndex;
}
