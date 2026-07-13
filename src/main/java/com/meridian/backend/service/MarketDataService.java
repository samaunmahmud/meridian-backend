package com.meridian.backend.service;

import com.meridian.backend.client.AlphaVantageClient;
import com.meridian.backend.client.GlobalQuote;
import com.meridian.backend.client.GlobalQuoteResponse;
import com.meridian.backend.dto.PricePointResponse;
import com.meridian.backend.dto.TickerResponse;
import com.meridian.backend.exception.TickerNotFoundException;
import com.meridian.backend.model.PriceHistory;
import com.meridian.backend.model.Ticker;
import com.meridian.backend.repository.PriceHistoryRepository;
import com.meridian.backend.repository.TickerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class MarketDataService {

    private static final Logger log = LoggerFactory.getLogger(MarketDataService.class);

    private final TickerRepository tickerRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final AlphaVantageClient alphaVantageClient;

    public MarketDataService(TickerRepository tickerRepository,
                             PriceHistoryRepository priceHistoryRepository,
                             AlphaVantageClient alphaVantageClient) {
        this.tickerRepository = tickerRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.alphaVantageClient = alphaVantageClient;
    }

    public void pollAndStore(String symbol, String name, String exchange) {
        Ticker ticker = tickerRepository.findBySymbol(symbol)
                .orElseGet(() -> tickerRepository.save(new Ticker(symbol, name, exchange)));

        GlobalQuoteResponse response = alphaVantageClient.fetchQuote(symbol);
        GlobalQuote quote = response.getGlobalQuote();

        if (quote == null || quote.getPrice() == null) {
            log.warn("No quote data returned for {}", symbol);
            return;
        }

        BigDecimal price = new BigDecimal(quote.getPrice());
        PriceHistory priceHistory = new PriceHistory(ticker, price, Instant.now());
        priceHistoryRepository.save(priceHistory);

        log.info("Saved price for {}: {}", symbol, price);
    }

    public List<TickerResponse> getAllTickers() {
        return tickerRepository.findAll().stream()
                .map(t -> new TickerResponse(t.getSymbol(), t.getName(), t.getExchange()))
                .toList();
    }

    public List<PricePointResponse> getPriceHistory(String symbol) {
        Ticker ticker = tickerRepository.findBySymbol(symbol)
                .orElseThrow(() -> new TickerNotFoundException(symbol));

        return priceHistoryRepository.findByTickerIdOrderByRecordedAtDesc(ticker.getId()).stream()
                .map(p -> new PricePointResponse(p.getPrice(), p.getRecordedAt()))
                .toList();
    }
}