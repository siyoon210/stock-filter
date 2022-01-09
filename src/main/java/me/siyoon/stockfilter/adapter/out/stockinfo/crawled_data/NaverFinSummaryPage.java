package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoErrorException;
import org.jsoup.nodes.Document;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverFinSummaryPage {

    public final Document annualFinSummaryPage;
    public final Document quarterFinSummaryPage;

    public static NaverFinSummaryPage of(Document annualFinSummaryPage, Document quarterFinSummaryPage) {
        return new NaverFinSummaryPage(annualFinSummaryPage, quarterFinSummaryPage);
    }

    public Document document(Period period) {
        if (Period.YEARS.contains(period)) {
            return annualFinSummaryPage;
        }

        if (Period.QUARTERS.contains(period)) {
            return quarterFinSummaryPage;
        }

        throw new StockInfoErrorException("NaverFinSummaryPage. Invalid Period. " + period);
    }
}
