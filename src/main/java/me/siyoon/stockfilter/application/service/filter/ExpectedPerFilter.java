package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ExpectedPerCommand;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.Performance;
import org.springframework.stereotype.Component;

@Component
class ExpectedPerFilter implements StockFilterI {

    // {ExpectedPER}(예측(추정)PER)이 {periods} 기간동안 {threshold} 이하인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ExpectedPerCommand command = filterCommand.expectedPer;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(predicate(command, threshold));
    }

    private Predicate<Performance> predicate(ExpectedPerCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.per.isLessThan(threshold);
        };
    }

    private boolean unknownValuePass(ExpectedPerCommand command, Performance performance) {
        return command.unknownValuePass && performance.per == PER.UNKNOWN_VALUE;
    }
}
