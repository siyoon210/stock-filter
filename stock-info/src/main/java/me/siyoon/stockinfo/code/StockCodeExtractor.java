package me.siyoon.stockinfo.code;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.siyoon.stockinfo.Main.LOGGER;

public class StockCodeExtractor {
    public List<String> getStockCodes() {
        try {
            List<String> stockCodes = new ArrayList<>();
            File corpCodeFile = new File("src/main/resources/CORPCODE.xml");
            Document document = Jsoup.parse(Files.readString(corpCodeFile.toPath()), "", Parser.xmlParser());
            Elements list = document.getElementsByTag("result").get(0)
                    .getElementsByTag("list");

            for (Element element : list) {
                String stockCode = element.getElementsByTag("stock_code").get(0).text();
                if (" ".equals(stockCode) || stockCode.isBlank()) {
                    continue;
                }

                try {
                    stockCodes.add(stockCode);
                } catch (NumberFormatException e) {
                    continue;
                }
            }

            return stockCodes;
        } catch (Exception e) {
            LOGGER.info("CORPCODE 읽기 실패. " + e);
            return Collections.emptyList();
        }
    }
}
