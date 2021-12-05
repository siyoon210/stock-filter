package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;

@ToString
public class StockFilterCommand {

    public final NetIncomeCommand netIncome;  // 당기순이익
    public final ExpectedDividendYieldCommand expectedDividendYield; // 예상 배당률

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StockFilterCommand(NetIncomeCommand netIncome,
                              ExpectedDividendYieldCommand expectedDividendYield) {
        this.netIncome = netIncome;
        this.expectedDividendYield = expectedDividendYield;
    }

    @ToString
    public static class NetIncomeCommand { // 당기순이익

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public NetIncomeCommand(boolean skip, boolean unknownValuePass, List<Period> periods,
                                Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    public static class ExpectedDividendYieldCommand { // 예상 배당률

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods = List.of(Period.THIS_YEAR);
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ExpectedDividendYieldCommand(boolean skip, boolean unknownValuePass,
                                            Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.threshold = threshold;
        }
    }
}
