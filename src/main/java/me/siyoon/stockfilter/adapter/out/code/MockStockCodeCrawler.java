package me.siyoon.stockfilter.adapter.out.code;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

public class MockStockCodeCrawler implements StockCodeReader {

    @Override
    public List<String> stockCodes() {
        return List.of("001750", "003545");
    }
}
