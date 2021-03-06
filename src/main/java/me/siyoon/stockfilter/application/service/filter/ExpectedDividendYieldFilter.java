package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.ExpectedDividendYieldCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.DPS;
import me.siyoon.stockfilter.domain.performance.DividendYield;
import me.siyoon.stockfilter.domain.performance.Performance;
import org.springframework.stereotype.Component;

@Component
class ExpectedDividendYieldFilter implements StockFilterI {

    // {expectedDividendYield}(예상 배당률)이 THIS_YEAR 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ExpectedDividendYieldCommand command = filterCommand.expectedDividendYield;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double price = stockInfo.price();

        return performances.stream()
                           .allMatch(expectedDividendYieldPredicate(command, price));
    }

    private Predicate<Performance> expectedDividendYieldPredicate(
            ExpectedDividendYieldCommand command,
            Double price) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return expectedDividendYield(price, performance).isGreaterThan(command.threshold);
        };
    }

    private boolean unknownValuePass(ExpectedDividendYieldCommand command,
                                     Performance performance) {
        return command.unknownValuePass && performance.dps == DPS.UNKNOWN_VALUE;
    }

    private DividendYield expectedDividendYield(Double price, Performance performance) {
        return performance.dps.expectedDividendYield(price);
    }
}
