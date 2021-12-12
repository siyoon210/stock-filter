package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ImprovedPerCommand;
import me.siyoon.stockfilter.domain.PER;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import org.springframework.stereotype.Component;

@Component
class ImprovedPerFilter implements StockFilterI {

    // PER이 개선되었는가?
    // LAST_YEAR 대비해서 THIS_YEAR 예상(추정) PER이 {ratio}만큼 떨어졌는가?
    @Override
    public boolean passed(StockFilterCommand filterCommand, StockInfo stockInfo) {
        ImprovedPerCommand command = filterCommand.improvedPer;

        if (!command.test) {
            return true;
        }

        PER lastYearPER = stockInfo.performanceOf(Period.LAST_YEAR).per;
        PER thisYearPER = stockInfo.performanceOf(Period.THIS_YEAR).per;

        if (isUnknownValue(lastYearPER, thisYearPER)) {
            return false;
        }

        return test(command, lastYearPER, thisYearPER);
    }

    private boolean test(ImprovedPerCommand command, PER lastYearPER, PER thisYearPER) {
        // (PER)이 양수로 전환된 경우는 pass로 간주한다.
        if (lastYearPER.isNegative() && thisYearPER.isPositive()) {
            return true;
        }

        if (thisYearPER.isNegative()) {
            return false;
        }

        return improvedRatio(lastYearPER, thisYearPER) > command.ratio;
    }

    private double improvedRatio(PER lastYearPER, PER thisYearPER) {
        return 100 - (thisYearPER.dividedBy(lastYearPER) * 100);
    }

    private boolean isUnknownValue(PER lastYearPER,
                                   PER thisYearPER) {
        return lastYearPER == PER.UNKNOWN_VALUE || thisYearPER == PER.UNKNOWN_VALUE;
    }
}
