package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.DebtRatioCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DebtRatio;
import me.siyoon.stockfilter.domain.performance.Performance;
import org.springframework.stereotype.Component;

@Component
class DebtRatioFilter implements StockFilterI {

    // {debtRatio}(부채비율)이 {periods} 기간동안 {threshold} 이하인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        DebtRatioCommand command = filterCommand.debtRatio;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(debtRatioPredicate(command, threshold));
    }

    private Predicate<Performance> debtRatioPredicate(DebtRatioCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.debtRatio.isLessThan(threshold);
        };
    }

    private boolean unknownValuePass(DebtRatioCommand command, Performance performance) {
        return command.unknownValuePass && performance.debtRatio == DebtRatio.UNKNOWN_VALUE;
    }
}
