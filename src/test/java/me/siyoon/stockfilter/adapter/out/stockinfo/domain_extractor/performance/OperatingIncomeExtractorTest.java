package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.OperatingIncome;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class OperatingIncomeExtractorTest {

    private static Document NAVER_ANNUAL_FIN_SUMMARY_PAGE;
    private static Document NAVER_QUARTER_FIN_SUMMARY_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        NAVER_ANNUAL_FIN_SUMMARY_PAGE = Jsoup.parse(Files.readString(new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/naver-company-state-annual-financial-summary-sample.html")
                                                                             .toPath()));

        NAVER_QUARTER_FIN_SUMMARY_PAGE = Jsoup.parse(Files.readString(new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/naver-company-state-quarter-financial-summary-sample.html")
                                                                              .toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void operatingIncome_test(Period period, OperatingIncome expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverAnnualFinSummaryPage(
                                                     NAVER_ANNUAL_FIN_SUMMARY_PAGE)
                                             .naverQuarterFinSummaryPage(
                                                     NAVER_QUARTER_FIN_SUMMARY_PAGE)
                                             .build();

        // when
        OperatingIncome actual = OperatingIncomeExtractor.operatingIncome(crawledData, period);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> operatingIncome_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, OperatingIncome.from(165.0)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, OperatingIncome.from(235.0)),
                Arguments.of(Period.LAST_QUARTER, OperatingIncome.from(62.0)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, OperatingIncome.UNKNOWN_VALUE)
        );
    }

    @Test
    void averageGrowthRateFrom_test() {
        OperatingIncome threeYearsAgoOperatingIncome = OperatingIncome.from(11550.0);
        OperatingIncome lastYearOperatingIncome = OperatingIncome.from(13255.0);

        Double growthRate = lastYearOperatingIncome.averageGrowthRateFrom(threeYearsAgoOperatingIncome, 2);
        assertThat(growthRate).isEqualTo(0.07126093926736643);
    }
}