package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.AnnualPriceVolatilityCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class AnnualPriceVolatilityFilter implements StockFilterI {

    // 52주 최고가{annualHigh} / 52주 최저가 {annualLow}의 값이 {threshold} 이하인가? (로직상1이 최저임)
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        AnnualPriceVolatilityCommand command = filterCommand.annualPriceVolatility;

        if (!command.test) {
            return true;
        }

        Double threshold = command.threshold;
        return stockInfo.tradingInfo.annualHigh / stockInfo.tradingInfo.annualLow < threshold;
    }
}
