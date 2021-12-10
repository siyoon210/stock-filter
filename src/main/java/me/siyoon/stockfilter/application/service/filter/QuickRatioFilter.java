package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.QuickRatioCommand;
import me.siyoon.stockfilter.domain.DebtRatio;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.QuickRatio;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class QuickRatioFilter implements StockFilterI {

    // {quickRatio}(당좌비율)이 {periods} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        QuickRatioCommand command = filterCommand.quickRatio;

        if (command.skip) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(quickRatioPredicate(command, threshold));
    }

    private Predicate<Performance> quickRatioPredicate(QuickRatioCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.quickRatio.isGreaterThan(threshold);
        };
    }

    private boolean unknownValuePass(QuickRatioCommand command, Performance performance) {
        return command.unknownValuePass && performance.quickRatio == QuickRatio.UNKNOWN_VALUE;
    }
}
