package me.siyoon.stockfilter.adapter.out.stockinfo.naver.crawler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

@Builder
@AllArgsConstructor
public class CrawledData {
    public final String stockCode;
    public final Document mainPage;
    public final Element yearlyFinancialSummary;
    public final Element quarterFinancialSummary;
    public final Element yearlyStabilityIndex;
    public final Element quarterStabilityIndex;
}
