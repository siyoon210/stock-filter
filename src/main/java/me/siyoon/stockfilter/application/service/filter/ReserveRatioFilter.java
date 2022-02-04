package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.ReserveRatioCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.ReserveRatio;
import org.springframework.stereotype.Component;

@Component
class ReserveRatioFilter implements StockFilterI {

    // {reserveRatio}(유보율)이 {periods} 기간동안 {threshold} 이상인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ReserveRatioCommand command = filterCommand.reserveRatio;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(predicate(command, threshold));
    }

    private Predicate<Performance> predicate(ReserveRatioCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.reserveRatio.isGreaterThan(threshold);
        };
    }

    private boolean unknownValuePass(ReserveRatioCommand command, Performance performance) {
        return command.unknownValuePass && performance.reserveRatio == ReserveRatio.UNKNOWN_VALUE;
    }
}
