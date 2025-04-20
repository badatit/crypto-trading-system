package com.example.trading.scheduler.provider.impl;

import com.example.trading.mapper.AggregatePriceMapper;
import com.example.trading.model.dto.AggregatedPriceDTO;
import com.example.trading.model.dto.HuobiPriceDTO;
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
public class HuobiPriceProvider implements FetchDataStrategy {

    @Value("${external.huobi.api.url}")
    private String huobiUrl;

    private final RestTemplate restTemplate;

    private final AggregatePriceMapper mapper;

    @Override
    public AggregatedPriceDTO fetchPrice(String symbol) {
        HuobiPriceDTO response = this.fetchHuobiPriceResponse();
        if (Objects.isNull(response) || this.isDataEmpty(response)) {
            return new AggregatedPriceDTO();
        }

        return response.getData().stream()
                .filter(dto -> Objects.equals(dto.getSymbol().toLowerCase(), symbol.toLowerCase()))
                .map(mapper::mapToAggregatedPriceDTO).findFirst().orElse(new AggregatedPriceDTO());

    }

    private HuobiPriceDTO fetchHuobiPriceResponse() {
        ResponseEntity<HuobiPriceDTO> response = restTemplate.exchange(
                huobiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<HuobiPriceDTO>() {
                }
        );
        return response.getBody();
    }

    private boolean isDataEmpty(HuobiPriceDTO response) {
        List<HuobiPriceDTO.HuobiPriceData> data = response.getData();
        return Objects.isNull(data) || data.isEmpty();
    }
}
