package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.NetIncomeCommand;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import org.springframework.stereotype.Component;

@Component
class NetIncomeFilter implements StockFilterI {

    // {netIncome}(당기순이익)이 {periods} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        NetIncomeCommand command = filterCommand.netIncome;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(netIncomePredicate(command, threshold));
    }

    private Predicate<Performance> netIncomePredicate(NetIncomeCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.netIncome.isGreaterThan(threshold);
        };
    }

    private boolean unknownValuePass(NetIncomeCommand command, Performance performance) {
        return command.unknownValuePass && performance.netIncome == NetIncome.UNKNOWN_VALUE;
    }
}
