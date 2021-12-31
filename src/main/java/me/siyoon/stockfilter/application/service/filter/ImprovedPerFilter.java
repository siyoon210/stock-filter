package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ImprovedPerCommand;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.domain.performance.PER;
import org.springframework.stereotype.Component;

@Component
class ImprovedPerFilter implements StockFilterI {

    // {basePeriod} 대비해서 {targetPeriod} PER이 {ratio}만큼 떨어졌는가?
    // PER은 현재주가를 기준으로 계산된다. (현재주가 / Period EPS)
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ImprovedPerCommand command = filterCommand.improvedPer;

        if (!command.test) {
            return true;
        }

        EPS basePeriodEPS = stockInfo.performanceOf(command.basePeriod).eps;
        EPS targetPeriodEPS = stockInfo.performanceOf(command.targetPeriod).eps;

        if (unknownValuePass(command, basePeriodEPS, targetPeriodEPS)) {
            return true;
        }

        Double price = stockInfo.price();
        PER basePeriodPER = basePeriodEPS.calculatedPer(price);
        PER targetPeriodPER = targetPeriodEPS.calculatedPer(price);

        return test(command, basePeriodPER, targetPeriodPER);
    }

    private boolean unknownValuePass(ImprovedPerCommand command, EPS basePeriodEPS,
                                     EPS targetPeriodEPS) {
        return command.unknownValuePass &&
                (basePeriodEPS == EPS.UNKNOWN_VALUE || targetPeriodEPS == EPS.UNKNOWN_VALUE);
    }

    private boolean test(ImprovedPerCommand command, PER lastYearPER, PER thisYearPER) {
        // (PER)이 양수로 전환된 경우는 pass로 간주한다.
        if (lastYearPER.isNegative() && thisYearPER.isPositive()) {
            return true;
        }

        if (lastYearPER == PER.UNKNOWN_VALUE || thisYearPER == PER.UNKNOWN_VALUE) {
            return false;
        }

        return thisYearPER.improvedRatioComparedTo(lastYearPER) > command.ratio;
    }
}
