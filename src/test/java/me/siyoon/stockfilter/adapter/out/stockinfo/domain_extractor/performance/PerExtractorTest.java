package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.PER;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class PerExtractorTest {

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
    void per_test(Period period, PER expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverAnnualFinSummaryPage(
                                                     NAVER_ANNUAL_FIN_SUMMARY_PAGE)
                                             .naverQuarterFinSummaryPage(
                                                     NAVER_QUARTER_FIN_SUMMARY_PAGE)
                                             .build();

        // when
        PER actual = PerExtractor.per(crawledData, period);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> per_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, PER.from(12.31)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, PER.from(9.82)),
                Arguments.of(Period.LAST_QUARTER, PER.from(8.79)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, PER.UNKNOWN_VALUE)
        );
    }
}