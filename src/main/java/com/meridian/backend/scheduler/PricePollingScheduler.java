package com.meridian.backend.scheduler;

import com.meridian.backend.service.MarketDataService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PricePollingScheduler {

    private static final List<String[]> WATCHLIST = List.of(
            new String[]{"IBM", "International Business Machines", "NYSE"},
            new String[]{"AAPL", "Apple Inc.", "NASDAQ"},
            new String[]{"MSFT", "Microsoft Corporation", "NASDAQ"}
    );

    private int index = 0;

    private final MarketDataService marketDataService;

    public PricePollingScheduler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @Scheduled(fixedRate = 20000)
    public void pollNextTicker() {
        String[] entry = WATCHLIST.get(index);
        index = (index + 1) % WATCHLIST.size();

        marketDataService.pollAndStore(entry[0], entry[1], entry[2]);
    }
}