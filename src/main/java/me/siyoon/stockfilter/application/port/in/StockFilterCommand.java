package me.siyoon.stockfilter.application.port.in;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import lombok.Builder;
import lombok.ToString;
import me.siyoon.stockfilter.domain.Period;

@ToString
@Builder
public class StockFilterCommand {

    public final OperatingIncomeCommand operatingIncome; // 영업이익
    public final NetIncomeCommand netIncome;  // 당기순이익
    public final DebtRatioCommand debtRatio; // 부채비율
    public final QuickRatioCommand quickRatio; // 당좌비율
    public final PerCommand per; // PER
    public final ExpectedPerCommand expectedPer; // 예상(추정) PER
    public final DividendYieldCommand dividendYield; // 시가배당률
    public final ExpectedDividendYieldCommand expectedDividendYield; // 예상 배당률

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public StockFilterCommand(
            OperatingIncomeCommand operatingIncome,
            NetIncomeCommand netIncome,
            DebtRatioCommand debtRatio,
            QuickRatioCommand quickRatio,
            PerCommand per,
            ExpectedPerCommand expectedPer,
            DividendYieldCommand dividendYield,
            ExpectedDividendYieldCommand expectedDividendYield) {
        this.operatingIncome = operatingIncome;
        this.netIncome = netIncome;
        this.debtRatio = debtRatio;
        this.quickRatio = quickRatio;
        this.per = per;
        this.expectedPer = expectedPer;
        this.dividendYield = dividendYield;
        this.expectedDividendYield = expectedDividendYield;
    }

    @ToString
    @Builder
    public static class OperatingIncomeCommand { // 영업이익

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public OperatingIncomeCommand(boolean skip, boolean unknownValuePass, List<Period> periods,
                                      Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
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
    @Builder
    public static class DebtRatioCommand { // 부채비율

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DebtRatioCommand(boolean skip, boolean unknownValuePass,
                                List<Period> periods,
                                Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class QuickRatioCommand { // 당좌비율

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public QuickRatioCommand(boolean skip, boolean unknownValuePass,
                                List<Period> periods,
                                Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class PerCommand { // PER

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public PerCommand(boolean skip, boolean unknownValuePass,
                                 List<Period> periods,
                                 Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.periods = periods;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class ExpectedPerCommand { // 예상(추정) PER

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods = List.of(Period.THIS_YEAR);
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ExpectedPerCommand(boolean skip, boolean unknownValuePass,
                          Double threshold) {
            this.skip = skip;
            this.unknownValuePass = unknownValuePass;
            this.threshold = threshold;
        }
    }

    @ToString
    @Builder
    public static class DividendYieldCommand { // 시가배당률

        public final boolean skip;
        public final boolean unknownValuePass;
        public final List<Period> periods;
        public final Double threshold;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public DividendYieldCommand(boolean skip, boolean unknownValuePass,
                                List<Period> periods,
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
