package me.siyoon.stockinfo;

import me.siyoon.stockinfo.code.StockCodeExtractor;
import me.siyoon.stockinfo.naver.shark.Shark;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    public static final Logger LOGGER = Logger.getGlobal();

    public static void main(String[] args) {
        StockCodeExtractor stockCodeExtractor = new StockCodeExtractor();
        List<String> stockCodes = stockCodeExtractor.getStockCodes();

        StringBuilder result = new StringBuilder();
        Shark shark = new Shark();

        List<Boolean> collect = stockCodes.parallelStream()
                .map(code -> shark.run(code, result)).filter(b -> b).collect(Collectors.toList());
        LOGGER.info("collect.size() = " + collect.size() + "\n" + result);
    }
}
