package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class SalesRevenueExtractorTest {
    private static Document FN_GUIDE_MAIN_PAGE;

    @BeforeAll
    static void setUp() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/fnguide/fnguide-main-sample.html");
        FN_GUIDE_MAIN_PAGE = Jsoup.parse(Files.readString(sampleHtml.toPath()));
    }

    @ParameterizedTest
    @MethodSource
    void salesRevenue_test(Period period, SalesRevenue expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .fnGuideMainPage(FN_GUIDE_MAIN_PAGE)
                                             .build();

        // when
        SalesRevenue salesRevenue = SalesRevenueExtractor.salesRevenue(crawledData, period);

        // then
        assertThat(salesRevenue).isEqualTo(expected);
    }

    private static Stream<Arguments> salesRevenue_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, SalesRevenue.from(24027.0)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, SalesRevenue.from(26533.0)),
                Arguments.of(Period.LAST_QUARTER, SalesRevenue.from(6826.0)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, SalesRevenue.from(7380.0))
        );
    }
}