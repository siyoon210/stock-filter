package me.siyoon.stockfilter.adapter.out.stockinfo.naver.converter.performance;

import me.siyoon.stockfilter.domain.performance.Performances2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceParserTest {

    private final PerformanceParser dut = new PerformanceParser();

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new HtmlUnitDriver(true);
    }

    @Test
    void financialPerformances_test() {
        // given
        String code = "306200";

        // when
        Performances2 performances2 = dut.performances(driver, code);

        // then
        assertThat(performances2).isNotNull();
        System.out.println(performances2);
    }

    @AfterEach
    void afterEach() {
        driver.quit();
    }
}
