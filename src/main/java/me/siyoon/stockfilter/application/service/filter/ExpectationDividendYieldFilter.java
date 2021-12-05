package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ExpectedDividendYieldCommand;
import me.siyoon.stockfilter.domain.DividendYield;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class ExpectationDividendYieldFilter implements StockFilterI {

    // {expectedDividendYield}(예상 배당률)이 THIS_YEAR 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ExpectedDividendYieldCommand expectedDividendYieldCommand = filterCommand.expectedDividendYield;

        List<Performance> performances
                = stockInfo.performancesIn(expectedDividendYieldCommand.periods);
        Double price = stockInfo.price();
        Double threshold = expectedDividendYieldCommand.threshold;

        return performances.stream()
                           .allMatch(expectedDividendYieldPredicate(price, threshold));
    }

    private Predicate<Performance> expectedDividendYieldPredicate(Double price, Double threshold) {
        return performance -> expectedDividendYield(price, performance).isGreaterThan(threshold);
    }

    private DividendYield expectedDividendYield(Double price, Performance performance) {
        return performance.dps.expectedDividendYield(price);
    }
}
