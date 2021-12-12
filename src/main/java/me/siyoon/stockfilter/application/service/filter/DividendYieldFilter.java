package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.DividendYieldCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ExpectedDividendYieldCommand;
import me.siyoon.stockfilter.domain.DPS;
import me.siyoon.stockfilter.domain.DividendYield;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class DividendYieldFilter implements StockFilterI {

    // {DividendYield}(시가 배당률)이 {period} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        DividendYieldCommand command = filterCommand.dividendYield;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);

        return performances.stream()
                           .allMatch(expectedDividendYieldPredicate(command));
    }

    private Predicate<Performance> expectedDividendYieldPredicate(DividendYieldCommand command) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.dividendYield.isGreaterThan(command.threshold);
        };
    }

    private boolean unknownValuePass(DividendYieldCommand command, Performance performance) {
        return command.unknownValuePass && performance.dividendYield == DividendYield.UNKNOWN_VALUE;
    }
}
