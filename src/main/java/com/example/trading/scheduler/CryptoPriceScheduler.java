package com.example.trading.scheduler;

import com.example.trading.enums.CryptoEnums;
import com.example.trading.model.dto.AggregatedPriceDTO;
import com.example.trading.model.entity.CryptoPriceEntity;
import com.example.trading.repository.CryptoPriceRepository;
import com.example.trading.scheduler.provider.FetchDataStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoPriceScheduler {

    private final List<FetchDataStrategy> fetchDataStrategies;
    private final CryptoPriceRepository cryptoPriceRepository;

    @Scheduled(fixedRate = 10000)
    public void priceAggregation() {
        for (String symbol : CryptoEnums.getKeys()) {
            List<AggregatedPriceDTO> prices = this.fetchPriceBySymbol(symbol);
            if (prices.size() < 2) {
                continue;
            }
            AggregatedPriceDTO bestBid = this.getBestBid(prices);
            AggregatedPriceDTO bestAsk = this.getBestAsk(prices);
            log.info("Fetch new data with best bid: {} and bestAsk: {}", bestBid, bestAsk);
            this.saveRecord(symbol, bestBid, bestAsk);
        }
    }

    private void saveRecord(String symbol, AggregatedPriceDTO bestBid, AggregatedPriceDTO bestAsk) {
        CryptoPriceEntity record = new CryptoPriceEntity();
        record.setSymbol(symbol);
        record.setBestBid(bestBid.getBid());
        record.setBestAsk(bestAsk.getAsk());
        record.setSourceBid(bestBid.getSource());
        record.setSourceAsk(bestAsk.getSource());
        cryptoPriceRepository.save(record);
    }

    private List<AggregatedPriceDTO> fetchPriceBySymbol(String symbol) {
        List<AggregatedPriceDTO> prices = new ArrayList<>();
        for (FetchDataStrategy fetchDataStrategy : fetchDataStrategies) {
            if (Objects.isNull(fetchDataStrategy)) {
                continue;
            }
            try {
                prices.add(fetchDataStrategy.fetchPrice(symbol));
            } catch (Exception e) {
                log.error("Error from provider: {}: {}", fetchDataStrategy.getClass().getSimpleName(), e.getMessage());
            }
        }
        return prices;
    }

    private AggregatedPriceDTO getBestAsk(List<AggregatedPriceDTO> prices) {
        return Collections.min(prices, Comparator.comparingDouble(AggregatedPriceDTO::getAsk));
    }

    private AggregatedPriceDTO getBestBid(List<AggregatedPriceDTO> prices) {
        return Collections.max(prices, Comparator.comparingDouble(AggregatedPriceDTO::getBid));
    }
}
