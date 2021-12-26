package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InvestIndexCrawlerTest {

    @InjectMocks
    private InvestIndexCrawler dut;

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new HtmlUnitDriver(BrowserVersion.CHROME, true);
    }

    @Test
    void quarterStabilityIndex_test() {
        // given
        // when
        Element element = dut.quarterStabilityIndex(driver, "065710");

        // then
        assertThat(element).isNotNull();
        System.out.println("element.html() = " + element.html());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}