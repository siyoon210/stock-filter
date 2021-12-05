package me.siyoon.stockfilter.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class TradingInfo { // 거래정보

    public final Double price; // 주가
    public final Integer tradingVolume; // 거래량
    public final Long transactionAmount; // 거래대금
}
