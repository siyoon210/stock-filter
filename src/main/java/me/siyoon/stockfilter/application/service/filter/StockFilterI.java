package me.siyoon.stockfilter.application.service.filter;

import me.siyoon.stockfilter.application.port.in.StockFilterCommand;
import me.siyoon.stockfilter.domain.StockInfo;

interface StockFilterI {

    boolean passed(StockFilterCommand stockFilterCommand, StockInfo stockInfo);
}
