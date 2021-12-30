package me.siyoon.stockfilter.application.service.filter;

import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.StockFilterCommand.ImprovedPerCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.TradingInfo;
import me.siyoon.stockfilter.domain.performance.EPS;
import me.siyoon.stockfilter.domain.performance.Performance;
import me.siyoon.stockfilter.domain.performance.Performances;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ImprovedPerFilterTest {

    @InjectMocks
    private ImprovedPerFilter dut;

    private static final double RATIO = 20;

    @ParameterizedTest(name = "(예상 PER)이 작년대비 RATIO(20) 이상 개선되었으면 pass")
    @MethodSource
    void passed_test(Double lastYearPER, Double thisYearPER, boolean expect) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand();
        StockInfo stockInfo = stockInfo(lastYearPER, thisYearPER);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expect);
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
                Arguments.of(55, 72, true),
                Arguments.of(100.0, 126.5, true),
                Arguments.of(20, 2.5, true),
                Arguments.of(-5.0, 30.0, true),
                Arguments.of(100.0, 125, false),
                Arguments.of(5.0, -30.0, false)
        );
    }

    private StockFilterCommand stockFilterCommand() {
        ImprovedPerCommand improvedPerCommand = ImprovedPerCommand.builder()
                                                                  .test(true)
                                                                  .ratio(RATIO)
                                                                  .build();
        return StockFilterCommand.builder()
                                 .improvedPer(improvedPerCommand)
                                 .build();
    }

    private StockInfo stockInfo(Double lastYearEPS, Double thisYearEPS) {
        Performance lastYearPerformance = Performance.builder()
                                                     .eps(EPS.from(lastYearEPS))
                                                     .build();

        Performance thisYearPerformance = Performance.builder()
                                                     .eps(EPS.from(thisYearEPS))
                                                     .build();

        Performances performances = new Performances(Map.of(Period.LAST_YEAR, lastYearPerformance,
                                                            Period.THIS_YEAR_EXPECTED, thisYearPerformance));

        return StockInfo.builder()
                        .tradingInfo(TradingInfo.builder()
                                                .price(1000.0)
                                                .build())
                        .performances(performances)
                        .build();
    }
}