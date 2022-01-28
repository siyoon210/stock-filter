package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.EpsIncreaseRateCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import org.springframework.stereotype.Component;

@Component
class EpsIncreaseRateFilter implements StockFilterI {

    // {basePeriod} 대비해서 {targetPeriod} EPS가 {rate}이상 증가하였는가?
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        EpsIncreaseRateCommand command = filterCommand.epsIncreaseRate;

        if (!command.test) {
            return true;
        }

        EPS basePeriodEPS = stockInfo.performanceOf(command.basePeriod).eps;
        EPS targetPeriodEPS = stockInfo.performanceOf(command.targetPeriod).eps;

        if (unknownValuePass(command, basePeriodEPS, targetPeriodEPS)) {
            return true;
        }

        return test(command, basePeriodEPS, targetPeriodEPS);
    }

    private boolean unknownValuePass(EpsIncreaseRateCommand command, EPS basePeriodEPS,
                                     EPS targetPeriodEPS) {
        return command.unknownValuePass &&
                (basePeriodEPS == EPS.UNKNOWN_VALUE || targetPeriodEPS == EPS.UNKNOWN_VALUE);
    }

    private boolean test(EpsIncreaseRateCommand command, EPS basePeriodEps, EPS targetPeriodEps) {
        if (basePeriodEps.equals(EPS.UNKNOWN_VALUE) || targetPeriodEps.equals(EPS.UNKNOWN_VALUE)) {
            return false;
        }

        if (turnedPositivePass(command, basePeriodEps, targetPeriodEps)) {
            return true;
        }

        if (basePeriodEps.isNegative() || basePeriodEps.isZero()) {
            return false;
        }

        return basePeriodEps.increaseRate(targetPeriodEps) >= command.rate;
    }

    private boolean turnedPositivePass(EpsIncreaseRateCommand command, EPS basePeriodEps, EPS targetPeriodEps) {
        return command.turnedPositivePass && basePeriodEps.isNegative() && targetPeriodEps.isPositive();
    }
}
