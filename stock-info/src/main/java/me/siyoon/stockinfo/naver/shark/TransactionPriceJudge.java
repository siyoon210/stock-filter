package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Document;

import static me.siyoon.stockinfo.Main.LOGGER;

public class TransactionPriceJudge {
    public static final String TRANSACTION_PRICE = "거래대금";

    private final Document document;

    public TransactionPriceJudge(Document document) {
        this.document = document;
    }

    //거래대금이 250(백만)보다 높은가
    public boolean pass(String code) {
        try {
            int txPrice = Integer.parseInt(document.getElementById("chart_area")
                    .getElementsByTag("table").get(0)
                    .getElementsByTag("tbody").get(0)
                    .getElementsByTag("tr").get(1)
                    .getElementsByTag("td").get(2)
                    .getElementsByTag("em").get(0)
                    .getElementsByClass("blind").get(0).text().replace(",", ""));

            if (txPrice <= 250) {
                LOGGER.info(TRANSACTION_PRICE + "이 250보다 작음");
                return false;
            }
        } catch (Exception e) {
            LOGGER.warning("Exception :" + TRANSACTION_PRICE + "(" + code + ")");
            return false;
        }

        return true;
    }
}
