package com.example.trading.scheduler.provider.impl;

import com.example.trading.mapper.AggregatePriceMapper;
import com.example.trading.model.dto.AggregatedPriceDTO;
import com.example.trading.model.dto.BinancePriceDTO;
import com.example.trading.scheduler.provider.FetchDataStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BinancePriceProvider implements FetchDataStrategy {

    @Value("${external.binance.api.url}")
    private String binanceUrl;

    private final RestTemplate restTemplate;

    private final AggregatePriceMapper mapper;

    @Override
    public AggregatedPriceDTO fetchPrice(String symbol) {
        ResponseEntity<List<BinancePriceDTO>> response = restTemplate.exchange(
                binanceUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BinancePriceDTO>>() {
                }
        );

        List<BinancePriceDTO> binancePriceDTOs = response.getBody();
        if (Objects.isNull(binancePriceDTOs) || binancePriceDTOs.isEmpty()) {
            return new AggregatedPriceDTO();
        }
        return binancePriceDTOs.stream()
                .filter(dto -> Objects.equals(dto.getSymbol().toLowerCase(), symbol.toLowerCase()))
                .map(mapper::mapToAggregatedPriceDTO).findFirst().orElse(new AggregatedPriceDTO());
    }
}
