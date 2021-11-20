package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class DebtRatioJudge {
    public static final String DEBT_RATIO = "부채비율";
    public static final int DEBT_RATIO_INDEX = 6;

    private final Element debtRatio;

    public DebtRatioJudge(Element companyAnalysisTable) {
        this.debtRatio = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(DEBT_RATIO_INDEX);
        validateElement(debtRatio, DEBT_RATIO);
    }

    //최근연도 부채비율이 100보다 낮은가
    public boolean pass() {
        int lastYearIndex = getLastYearIndex(debtRatio);
        if (lastYearIndex < 0) {
            return false;
        }
        try {
            double getDebtRatio = getNumberValue(debtRatio, lastYearIndex);

            if (getDebtRatio >= 100) {
                LOGGER.info(DEBT_RATIO + "가 100보다 큼");
                return false;
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("NumberFormatException\n" + debtRatio);
            return false;
        }

//        LOGGER.info(DEBT_RATIO + " 통과");
        return true;
    }
}
