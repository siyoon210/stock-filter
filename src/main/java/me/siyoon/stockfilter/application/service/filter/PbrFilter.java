package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.function.Predicate;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.PbrCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.PBR;
import me.siyoon.stockfilter.domain.performance.Performance;
import org.springframework.stereotype.Component;

@Component
class PbrFilter implements StockFilterI {

    // {PBR}이 {periods} 기간동안 {threshold} 이하인가
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        PbrCommand command = filterCommand.pbr;

        if (!command.test) {
            return true;
        }

        List<Performance> performances = stockInfo.performancesIn(command.periods);
        Double threshold = command.threshold;

        return performances.stream()
                           .allMatch(predicate(command, threshold));
    }

    private Predicate<Performance> predicate(PbrCommand command, Double threshold) {
        return performance -> {
            if (unknownValuePass(command, performance)) {
                return true;
            }
            return performance.pbr.isLessThan(threshold);
        };
    }

    private boolean unknownValuePass(PbrCommand command, Performance performance) {
        return command.unknownValuePass && performance.pbr == PBR.UNKNOWN_VALUE;
    }
}
