package me.siyoon.stockfilter.adapter.out.stockinfo.naver.performance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.Period;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NaverNetIncomeParserTest {

    private Element performanceTable;

    @BeforeEach
    void setUp() throws IOException {
        performanceTable = performanceTable();
    }

    private Element performanceTable() throws IOException {
        File sampleHtml = new File(
                "src/test/java/me/siyoon/stockfilter/resource/naver/sample-stock-info.html");
        Document document = Jsoup.parse(Files.readString(sampleHtml.toPath()));
        return NaverPerformanceParseHelper.performanceTable(document);
    }

    @Test
    void netIncome_test() {
        // given

        // when
        NetIncome netIncome = NaverNetIncomeParser.netIncome(Period.LAST_YEAR, performanceTable);

        // then
        assertThat(netIncome).isEqualTo(new NetIncome(1286.0));
    }
}