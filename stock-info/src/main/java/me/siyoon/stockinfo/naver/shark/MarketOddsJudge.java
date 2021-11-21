package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class MarketOddsJudge {
    public static final String MARKET_ODDS = "시가배당률(%)";
    public static final int MARKET_ODDS_INDEX = 14;

    private final Element marketOdds;

    public MarketOddsJudge(Element companyAnalysisTable) {
        this.marketOdds = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(MARKET_ODDS_INDEX);
        validateElement(marketOdds, MARKET_ODDS);
    }

    //최근 3~4개년도 시가배당률이 2보다 큰가
    public boolean pass(String code) {
        int lastYearIndex = getLastYearIndex(marketOdds);
        if (lastYearIndex < 0) {
            return false;
        }

        for (int i = 0; i <= lastYearIndex; i++) {
            try {
                double marketOdds = getNumberValue(this.marketOdds, i);
                if (marketOdds < 2) {
                    LOGGER.info(MARKET_ODDS + "이 2 보다 작음. " + marketOdds);
                    return false;
                }
            } catch (Exception e) {
                LOGGER.warning("Exception :" + MARKET_ODDS + "(" + code + ")");
                return false;
            }
        }
//        LOGGER.info(MARKET_ODDS + " 통과");
        return true;
    }
}
