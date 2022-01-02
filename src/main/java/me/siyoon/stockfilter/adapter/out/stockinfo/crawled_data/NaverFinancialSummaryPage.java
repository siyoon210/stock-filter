package me.siyoon.stockfilter.adapter.out.stockinfo.crawled_data;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoErrorException;
import org.jsoup.nodes.Document;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NaverFinancialSummaryPage {

    public final Document annualFinancialSummaryPage;
    public final Document quarterFinancialSummaryPage;

    public static NaverFinancialSummaryPage of(Document annualFinancialSummaryPage,
                                               Document quarterFinancialSummaryPage) {
        return new NaverFinancialSummaryPage(annualFinancialSummaryPage, quarterFinancialSummaryPage);
    }

    public Document document(Period period) {
        if (Period.YEARS.contains(period)) {
            return annualFinancialSummaryPage;
        }

        if (Period.QUARTERS.contains(period)) {
            return quarterFinancialSummaryPage;
        }

        throw new StockInfoErrorException("NaverFinancialSummaryPage. Invalid Period. " + period);
    }
}
