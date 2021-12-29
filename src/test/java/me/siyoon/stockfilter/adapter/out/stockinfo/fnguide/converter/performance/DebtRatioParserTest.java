package me.siyoon.stockfilter.adapter.out.stockinfo.fnguide.converter.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.DebtRatio;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class DebtRatioParserTest {

    private final DebtRatioParser dut = new DebtRatioParser();

    private static Document MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File("src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide_main_sample.html");
        MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void debtRatio_test(Period period, DebtRatio expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .mainPage(MAIN_PAGE)
                                             .build();

        // when
        DebtRatio debtRatio = dut.debtRatio(crawledData, period);

        // then
        assertThat(debtRatio).isEqualTo(expected);
    }

    private static Stream<Arguments> debtRatio_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, DebtRatio.from(79.73)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, DebtRatio.from(86.89)),
                Arguments.of(Period.LAST_QUARTER, DebtRatio.from(90.43)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, DebtRatio.UNKNOWN_VALUE)
        );
    }
}