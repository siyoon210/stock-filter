package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.NetIncomeCommand;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class NetIncomeFilter implements StockFilterI {

    // {netIncome}(당기순이익)이 {periods} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        NetIncomeCommand netIncomeCommand = filterCommand.netIncome;
        List<Performance> performances = stockInfo.performancesIn(netIncomeCommand.periods);
        Long threshold = netIncomeCommand.threshold;

        return performances.stream()
                           .allMatch(performance -> performance.netIncome.isGraterThan(threshold));
    }
}
