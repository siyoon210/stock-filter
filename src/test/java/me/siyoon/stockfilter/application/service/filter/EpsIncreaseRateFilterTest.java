package me.siyoon.stockfilter.application.service.filter;

import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.EpsIncreaseRateCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EpsIncreaseRateFilterTest {

    @InjectMocks
    private EpsIncreaseRateFilter dut;

    private static final double RATE = 20;

    @ParameterizedTest(name = "(EPS)가 작년대비 RATE(20) 이상 개선되었으면 pass")
    @MethodSource
    void passed_test(Double basePeriodEPS, Double targetPeriodEPS, boolean expect) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand(false);
        StockInfo stockInfo = stockInfo(basePeriodEPS, targetPeriodEPS);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
                Arguments.of(100.0, 121.0, true),
                Arguments.of(100.0, 120.0, true),
                Arguments.of(100.0, 119.0, false),
                Arguments.of(10.0, -30.0, false)
        );
    }

    private StockFilterCommand stockFilterCommand(boolean turnedPositivePass) {
        EpsIncreaseRateCommand command = EpsIncreaseRateCommand.builder()
                                                               .test(true)
                                                               .turnedPositivePass(turnedPositivePass)
                                                               .rate(RATE)
                                                               .basePeriod(Period.LAST_YEAR)
                                                               .targetPeriod(Period.THIS_YEAR_EXPECTED)
                                                               .build();
        return StockFilterCommand.builder()
                                 .epsIncreaseRate(command)
                                 .build();
    }

    private StockInfo stockInfo(Double basePeriodEPS, Double targetPeriodEPS) {
        Performance basePeriodPerformance = Performance.builder()
                                                       .eps(EPS.from(basePeriodEPS))
                                                       .build();

        Performance targetPeriodPerformance = Performance.builder()
                                                         .eps(EPS.from(targetPeriodEPS))
                                                         .build();

        Performances performances = new Performances(Map.of(Period.LAST_YEAR, basePeriodPerformance,
                                                            Period.THIS_YEAR_EXPECTED,
                                                            targetPeriodPerformance));

        return StockInfo.builder()
                        .tradingInfo(TradingInfo.builder()
                                                .price(1000.0)
                                                .build())
                        .performances(performances)
                        .build();
    }

    @ParameterizedTest(name = "흑자전환 테스트")
    @MethodSource
    void turnedPositivePass_test(boolean turnedPositivePass, Double basePeriodEPS,
                                 Double targetPeriodEPS, boolean expect) {
        // given
        StockFilterCommand command = stockFilterCommand(turnedPositivePass);
        StockInfo stockInfo = stockInfo(basePeriodEPS, targetPeriodEPS);

        // when
        boolean actual = dut.passed(command, stockInfo);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> turnedPositivePass_test() {
        return Stream.of(
                Arguments.of(false, -5.0, 5.0, false),
                Arguments.of(true, -5.0, 5.0, true)
        );
    }
}