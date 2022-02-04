package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.AnnualHigherCurrentPriceRatioCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
public class AnnualHigherCurrentPriceRatioFilter implements StockFilterI {

    // 52주 최고가{annualHigh} / 현재가 {price}의 값이 {threshold} 이하인가? (로직상1이 최저임)
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        AnnualHigherCurrentPriceRatioCommand command = filterCommand.annualHigherCurrentPriceRatio;

        if (!command.test) {
            return true;
        }

        double annualHigherCurrentPriceRatio =
                stockInfo.tradingInfo.annualHigh / stockInfo.tradingInfo.price;

        return command.minThreshold < annualHigherCurrentPriceRatio
                && command.maxThreshold > annualHigherCurrentPriceRatio;
    }
}
