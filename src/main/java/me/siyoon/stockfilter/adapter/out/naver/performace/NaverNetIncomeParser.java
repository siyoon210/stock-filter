package me.siyoon.stockfilter.adapter.out.naver.performace;

import java.util.Map;
import me.siyoon.stockfilter.domain.NetIncome;
import me.siyoon.stockfilter.domain.Period;
import me.siyoon.stockfilter.exception.StockInfoFatalException;
import me.siyoon.stockfilter.exception.StockInfoParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class NaverNetIncomeParser {

    public static final String NET_INCOME = "당기순이익";
    public static final int NET_INCOME_INDEX = 2;

    private static final Map<Period, Integer> INDEX_BY_PERIOD = Map.of(Period.LAST_YEAR, 2,
                                                                       Period.TWO_YEARS_AGO, 1,
                                                                       Period.THREE_YEARS_AGO, 0);

    public NetIncome netIncome(Period period, Element performanceTable) {
        Element netIncomes = netIncome(performanceTable);
        validateText(netIncomes);
        String text = netIncomes.getElementsByTag("td").get(INDEX_BY_PERIOD.get(period))
                                .text()
                                .replace(",", "");
        return new NetIncome(Double.valueOf(text));
    }

    private Element netIncome(Element performanceTable) {
        return performanceTable.getElementsByTag("tbody").get(0)
                               .getElementsByTag("tr").get(NET_INCOME_INDEX);
    }

    private void validateText(Element netIncomes) {
        String actualText = netIncomes.getElementsByTag("th").get(0).text();
        if (!NET_INCOME.equals(actualText)) {
            throw new StockInfoFatalException(NET_INCOME + " is not equal to expected text. text = " + actualText);
        }
    }
}
