package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class QuickRatioJudge {
    public static final String QUICK_RATIO = "당좌비율";
    public static final int QUICK_RATIO_INDEX = 7;

    private final Element quickRatio;

    public QuickRatioJudge(Element companyAnalysisTable) {
        this.quickRatio = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(QUICK_RATIO_INDEX);
        validateElement(quickRatio, QUICK_RATIO);
    }

    //최근연도 당좌비율이 100보다 높은가
    public boolean pass(String code) {
        int lastYearIndex = getLastYearIndex(quickRatio);
        if (lastYearIndex < 0) {
            return false;
        }
        try {
            double getDebtRatio = getNumberValue(quickRatio, lastYearIndex);

            if (getDebtRatio < 70) {
                LOGGER.info(QUICK_RATIO + "가 100보다 작음");
                return false;
            }
        } catch (Exception e) {
            LOGGER.warning("Exception :" + QUICK_RATIO + "(" + code + ")");
            return false;
        }

//        LOGGER.info(QUICK_RATIO + " 통과");
        return true;
    }
}
