package me.siyoon.stockinfo.naver.shark;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import static me.siyoon.stockinfo.Main.LOGGER;

public class Shark {
    private static final String URL = "https://finance.naver.com/item/main.nhn?code=";

    public boolean run(String code, StringBuilder result) {
        Document document = getDocument(code);
        String companyName = getCompanyName(document);
        if (companyName == null) {
            return false;
        }
        LOGGER.info(companyName + "(" + code + ")" + " 분석 시작");

        TransactionPriceJudge transactionPriceJudge = new TransactionPriceJudge(document);
        if (!transactionPriceJudge.pass(code)) {
            return false;
        }

        Element companyAnalysisTable = getCompanyAnalysisTable(document);
        if (companyAnalysisTable == null) {
            return false;
        }

        OperatingProfitJudge operatingProfitJudge = new OperatingProfitJudge(companyAnalysisTable);
        if (!operatingProfitJudge.pass(code)) {
            return false;
        }

        NetIncomeJudge netIncomeJudge = new NetIncomeJudge(companyAnalysisTable);
        if (!netIncomeJudge.pass(code)) {
            return false;
        }

        DebtRatioJudge debtRatioJudge = new DebtRatioJudge(companyAnalysisTable);
        if (!debtRatioJudge.pass(code)) {
            return false;
        }

        QuickRatioJudge quickRatioJudge = new QuickRatioJudge(companyAnalysisTable);
        if (!quickRatioJudge.pass(code)) {
            return false;
        }

        MarketOddsJudge marketOddsJudge = new MarketOddsJudge(companyAnalysisTable);
        if (!marketOddsJudge.pass(code)) {
            return false;
        }

        PBRJudge pbrJudge = new PBRJudge(companyAnalysisTable);
        if (!pbrJudge.pass(code)) {
            return false;
        }

        result.append(companyName+"("+code+") " + URL + code + "\n");
        return true;
    }

    private Document getDocument(String code) {
        try {
            return Jsoup.connect(URL + code).get();
        } catch (IOException e) {
            LOGGER.warning("URL 파싱 실패");
            return null;
        }
    }

    private String getCompanyName(Document document) {
        try {
            return document.getElementById("middle")
                    .getElementsByTag("h2").get(0)
                    .getElementsByTag("a").get(0).text();
        } catch (Exception e) {
            return null;
        }
    }

    private Element getCompanyAnalysisTable(Document document) {
        try {
            return document.getElementById("content")
                    .getElementsByClass("cop_analysis").get(0)
                    .getElementsByClass("sub_section").get(0)
                    .getElementsByTag("table").get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
