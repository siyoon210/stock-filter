package me.siyoon.stockfilter.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.performance.Performance2;
import me.siyoon.stockfilter.domain.performance.Performances2;
import me.siyoon.stockfilter.domain.stability.StabilityIndices;


@AllArgsConstructor
@Builder
@ToString
public class StockInfo {

    public final String name;
    public final String code;
    public final TradingInfo tradingInfo; // 거래 정보
    // 모든 기간 데이터는 Performances에 들어간다.
    // StockInfo는 데이터를 쓰기좋게 정제된 도메인이다.
    // 실제 하고 싶은건 application에서 진행한다.
    public final Performances performances; // 실적
    public final Performances2 performances2; // 실적
    public final StabilityIndices stabilityIndices; // 안정성 지표

    public List<Performance> performancesIn(List<Period> periods) {
        return performances.in(periods);
    }

    public List<Performance2> performancesIn2(List<Period> periods) {
        return performances2.in(periods);
    }

    public Performance performanceOf(Period period) {
        return performances.of(period);
    }

    public Performance2 performanceOf2(Period period) {
        return performances2.of(period);
    }

    public Double price() {
        return tradingInfo.price;
    }
}
