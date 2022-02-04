package me.siyoon.stockfilter.application.service.sorter;

import java.util.List;
import me.siyoon.stockfilter.application.port.in.RankByCode;
import me.siyoon.stockfilter.domain.StockInfo;

public interface StockRankExtractorI {

    RankByCode rankByCode(List<StockInfo> stockInfos);
}
