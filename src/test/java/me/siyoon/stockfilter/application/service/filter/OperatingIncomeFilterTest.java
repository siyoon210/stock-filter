package me.siyoon.stockfilter.application.service.filter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand;
import me.siyoon.stockfilter.application.port.in.dto.request.StockFilterCommand.OperatingIncomeCommand;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.StockInfo;
import me.siyoon.stockfilter.domain.performance.OperatingIncome;
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
class OperatingIncomeFilterTest {

    @InjectMocks
    private OperatingIncomeFilter dut;

    private static final double THRESHOLD = 100.0;

    @ParameterizedTest(name = "영업이익이 command 임계값(100)보다 높으면 pass")
    @MethodSource
    void passed_test(Double debtRatioValue, boolean expectedPass) {
        // given
        StockFilterCommand filterCommand = stockFilterCommand();
        StockInfo stockInfo = stockInfo(debtRatioValue);

        // when
        boolean actual = dut.passed(filterCommand, stockInfo);

        // then
        assertThat(actual).isEqualTo(expectedPass);
    }

    private StockFilterCommand stockFilterCommand() {
        OperatingIncomeCommand command = OperatingIncomeCommand.builder()
                                                               .test(true)
                                                               .unknownValuePass(false)
                                                               .periods(List.of(Period.LAST_YEAR))
                                                               .threshold(THRESHOLD)
                                                               .build();
        return StockFilterCommand.builder()
                                 .operatingIncome(command)
                                 .build();
    }

    private StockInfo stockInfo(Double operatingIncomeValue) {
        Performance performance = Performance.builder()
                                             .operatingIncome(
                                                     OperatingIncome.from(operatingIncomeValue))
                                             .build();
        Performances performances = new Performances(Map.of(Period.LAST_YEAR, performance));

        return StockInfo.builder()
                        .performances(performances)
                        .build();
    }

    private static Stream<Arguments> passed_test() {
        return Stream.of(
                Arguments.of(THRESHOLD + 0.1, true),
                Arguments.of(THRESHOLD, false),
                Arguments.of(THRESHOLD - 0.1, false)
        );
    }
}