package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class NetIncomeJudge {
    public static final String NET_INCOME = "당기순이익";
    public static final int NET_INCOME_INDEX = 2;

    private final Element netIncome;

    public NetIncomeJudge(Element companyAnalysisTable) {
        this.netIncome = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(NET_INCOME_INDEX);
        validateElement(netIncome, NET_INCOME);
    }

    //최근 3~4개년도 당기순이익이 0보다 큰가
    public boolean pass(String code) {
        int lastYearIndex = getLastYearIndex(netIncome);
        if (lastYearIndex < 0) {
            return false;
        }

        for (int i = 0; i <= lastYearIndex; i++) {
            try {
                double netIncomeValue = getNumberValue(netIncome, i);
                if (netIncomeValue < 0) {
                    LOGGER.info(NET_INCOME + "이 0 보다 작음 " + netIncomeValue);
                    return false;
                }
            } catch (Exception e) {
                LOGGER.warning("Exception :" + NET_INCOME + "(" + code + ")");
                return false;
            }
        }
//        LOGGER.info(NET_INCOME + " 통과");
        return true;
    }
}
