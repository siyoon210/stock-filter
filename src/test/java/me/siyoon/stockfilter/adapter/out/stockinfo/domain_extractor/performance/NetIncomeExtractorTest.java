package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.NetIncome;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class NetIncomeExtractorTest {

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
    void netIncome_test(Period period, NetIncome expected) {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverAnnualFinSummaryPage(
                                                     NAVER_ANNUAL_FIN_SUMMARY_PAGE)
                                             .naverQuarterFinSummaryPage(
                                                     NAVER_QUARTER_FIN_SUMMARY_PAGE)
                                             .build();

        // when
        NetIncome actual = NetIncomeExtractor.netIncome(crawledData, period);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> netIncome_test() {
        return Stream.of(
                Arguments.of(Period.LAST_YEAR, NetIncome.from(104.0)),
                Arguments.of(Period.THIS_YEAR_EXPECTED, NetIncome.from(150.0)),
                Arguments.of(Period.TWO_QUARTERS_AGO, NetIncome.from(50.0)),
                Arguments.of(Period.LAST_QUARTER, NetIncome.from(44.0)),
                Arguments.of(Period.THIS_QUARTER_EXPECTED, NetIncome.UNKNOWN_VALUE)
        );
    }

    @Test
    void increase_test() {
        NetIncome base = NetIncome.from(164776.0);
        NetIncome increase1 = base.increase(7.1);
        NetIncome increase2 = increase1.increase(7.1);
        NetIncome increase3 = increase2.increase(7.1);
        NetIncome increase4 = increase3.increase(7.1);

        assertThat(increase1).isEqualTo(NetIncome.from(176475.096));
        assertThat(increase2).isEqualTo(NetIncome.from(189004.827816));
        assertThat(increase3).isEqualTo(NetIncome.from(202424.170590936));
        assertThat(increase4).isEqualTo(NetIncome.from(216796.28670289245));
        assertThat(NetIncome.totalValue(base, increase1, increase2, increase3, increase4))
                .isEqualTo(949476.3811098284);
    }
}