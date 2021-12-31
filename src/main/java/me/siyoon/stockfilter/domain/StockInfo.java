package me.siyoon.stockfilter.domain;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;


@AllArgsConstructor
@Builder
@ToString
public class StockInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    public final String name;
    public final String code;
    public final TradingInfo tradingInfo; // 거래 정보
    public final Performances performances; // 실적

    public List<Performance> performancesIn(List<Period> periods) {
        return performances.in(periods);
    }

    public Performance performanceOf(Period period) {
        return performances.of(period);
    }

    public Double price() {
        return tradingInfo.price;
    }
}
