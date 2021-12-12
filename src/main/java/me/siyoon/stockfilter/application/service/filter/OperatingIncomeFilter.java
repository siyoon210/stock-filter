package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.OperatingIncomeCommand;
import me.siyoon.stockfilter.domain.OperatingIncome;
import me.siyoon.stockfilter.domain.Performance;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class OperatingIncomeFilter implements StockFilterI {

    // {operatingIncome}(영업이익)이 {periods} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        OperatingIncomeCommand command = filterCommand.operatingIncome;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(operatingIncomePredicate(command, threshold));
    }

    private Predicate<Performance> operatingIncomePredicate(OperatingIncomeCommand command,
                                                            Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.operatingIncome.isGreaterThan(threshold);
        };
    }

    private boolean unknownValuePass(OperatingIncomeCommand command, Performance performance) {
        return command.unknownValuePass
                && performance.operatingIncome == OperatingIncome.UNKNOWN_VALUE;
    }
}
