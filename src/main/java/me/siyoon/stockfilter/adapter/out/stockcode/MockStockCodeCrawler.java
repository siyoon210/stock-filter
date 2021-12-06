package me.siyoon.stockfilter.adapter.out.stockcode;

import java.util.List;

public class MockStockCodeCrawler implements StockCodeReader {

    @Override
    public List<String> stockCodes() {
        return List.of("039490", "003545");
    }
}
