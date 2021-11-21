package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class OperatingProfitJudge {
    public static final String OPERATING_PROFIT = "영업이익";
    public static final int OPERATING_PROFIT_INDEX = 1;

    private final Element operatingProfit;

    public OperatingProfitJudge(Element companyAnalysisTable) {
        this.operatingProfit = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(OPERATING_PROFIT_INDEX);
        validateElement(operatingProfit, OPERATING_PROFIT);
    }

    //최근 3~4개년도 영업이익이 0보다 큰가
    public boolean pass(String code) {
        int lastYearIndex = getLastYearIndex(operatingProfit);
        if (lastYearIndex < 0) {
            return false;
        }

        for (int i = 0; i <= lastYearIndex; i++) {
            try {
                double operatingProfitValue = getNumberValue(operatingProfit, i);
                if (operatingProfitValue < 0) {
                    LOGGER.info(OPERATING_PROFIT + "이 0 보다 작음 " + operatingProfitValue);
                    return false;
                }
            } catch (Exception e) {
                LOGGER.warning("Exception :" + OPERATING_PROFIT + "(" + code + ")");
                return false;
            }
        }
//        LOGGER.info(OPERATING_PROFIT+ " 통과");
        return true;
    }
}
