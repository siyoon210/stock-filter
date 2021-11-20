package me.siyoon.stockinfo.naver.shark;

import org.jsoup.nodes.Element;

import static me.siyoon.stockinfo.Main.LOGGER;
import static me.siyoon.stockinfo.util.ElementTextValidator.validateElement;
import static me.siyoon.stockinfo.util.LastYearIndexExtractor.getLastYearIndex;
import static me.siyoon.stockinfo.util.NumberExtractor.getNumberValue;

public class PBRJudge {
    public static final String PBR = "PBR(배)";
    public static final int PBR_INDEX = 12;

    private final Element pbr;

    public PBRJudge(Element companyAnalysisTable) {
        this.pbr = companyAnalysisTable.getElementsByTag("tbody").get(0)
                .getElementsByTag("tr").get(PBR_INDEX);
        validateElement(pbr, PBR);
    }

    //최근연도 PBR이 1보다 작은가
    public boolean pass() {
        int lastYearIndex = getLastYearIndex(pbr);
        if (lastYearIndex < 0) {
            return false;
        }
        try {
            double pbr = getNumberValue(this.pbr, lastYearIndex);

            if (pbr > 1) {
                LOGGER.info(PBR + "이 1보다 큼");
                return false;
            }
        } catch (NumberFormatException e) {
            LOGGER.warning("NumberFormatException\n" + pbr);
            return false;
        }

//        LOGGER.info(PBR + " 통과");
        return true;
    }
}
