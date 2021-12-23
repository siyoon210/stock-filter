package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
public class TradingInfo { // 거래정보

    public final Double marketCap; // 시가총액
    public final Double price; // 주가
    public final Long numberOfShare; // 주식수
    public final Long tradingVolume; // 거래량
    public final Double transactionAmount; // 거래대금
    public final Double annualHigh; // 52주 최고가
    public final Double annualLow; // 52주 최저가

    @Builder
    public TradingInfo(Double price, Long numberOfShare, Long tradingVolume,
                       Double transactionAmount) {
        this.marketCap = price * numberOfShare;
        this.price = price;
        this.numberOfShare = numberOfShare;
        this.tradingVolume = tradingVolume;
        this.transactionAmount = transactionAmount;
    }
}
