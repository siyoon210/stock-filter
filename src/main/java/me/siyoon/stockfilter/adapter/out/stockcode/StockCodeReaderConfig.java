package me.siyoon.stockfilter.adapter.out.stockcode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StockCodeReaderConfig {

    @Bean
    @Profile("test")
    public MockStockCodeCrawler mockStockCodeCrawler() {
        return new MockStockCodeCrawler();
    }

    @Bean
    @Profile("!test")
    public NaverStockCodeCrawler naverStockCodeCrawler() {
        return new NaverStockCodeCrawler();
    }
}
