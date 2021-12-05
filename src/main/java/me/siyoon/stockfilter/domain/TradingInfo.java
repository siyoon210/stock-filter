package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Builder
public class TradingInfo { // 거래정보

    public final Double price; // 주가
    public final Long tradingVolume; // 거래량
    public final Double transactionAmount; // 거래대금
}
