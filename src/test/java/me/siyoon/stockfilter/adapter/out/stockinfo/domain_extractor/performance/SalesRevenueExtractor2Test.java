package me.siyoon.stockfilter.adapter.out.stockinfo.domain_extractor.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data.CrawledData;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.domain.performance.SalesRevenue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesRevenueExtractor2Test {

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

    @Test
    void _test() {
        // given
        CrawledData crawledData = CrawledData.builder()
                                             .naverAnnualFinancialSummaryPage(
                                                     NAVER_ANNUAL_FIN_SUMMARY_PAGE)
                                             .naverQuarterFinancialSummaryPage(
                                                     NAVER_QUARTER_FIN_SUMMARY_PAGE)
                                             .build();
        Period period = Period.LAST_YEAR;

        // when
        SalesRevenue salesRevenue = SalesRevenueExtractor2.salesRevenue(crawledData, period);

        // then
        //2,326
        assertThat(salesRevenue).isEqualTo(SalesRevenue.from(2326.0));
    }
}