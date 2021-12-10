package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.PerCommand;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class PerFilter implements StockFilterI {

    // {PER}이 {periods} 기간동안 {threshold} 이하인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        PerCommand command = filterCommand.per;

        if (command.skip) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(predicate(command, threshold));
    }

    private Predicate<Performance> predicate(PerCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.per.isLessThan(threshold);
        };
    }

    private boolean unknownValuePass(PerCommand command, Performance performance) {
        return command.unknownValuePass && performance.per == PER.UNKNOWN_VALUE;
    }
}
