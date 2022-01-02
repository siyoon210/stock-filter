package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.jsoup.nodes.Document;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverMainPage {

    private final Document document;

    public static NaverMainPage from(Document document) {
        return new NaverMainPage(document);
    }

    public Document document() {
        return document;
    }
}
