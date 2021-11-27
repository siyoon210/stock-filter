package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class PERJudge {
    public static final String PER = "PER(배)";
    public static final int PER_INDEX = 10;

    private final Element per;

    public PERJudge(Element companyAnalysisTable) {
        this.per = companyAnalysisTable.getElementsByTag("tbody").get(0)
                                       .getElementsByTag("tr").get(PER_INDEX);
        validateElement(per, PER);
    }

    //최근연도 PER이 10보다 작은가
    public boolean pass(String code) {
        int lastYearIndex = getLastYearIndex(per);
        if (lastYearIndex < 0) {
            return false;
        }
        try {
            double pbr = getNumberValue(this.per, lastYearIndex);

            if (pbr > 10) {
                LOGGER.info(PER + "이 10보다 큼");
                return false;
            }
        } catch (Exception e) {
            LOGGER.warning("Exception :" + PER + "(" + code + ")");
            return false;
        }

//        LOGGER.info(PBR + " 통과");
        return true;
    }
}
