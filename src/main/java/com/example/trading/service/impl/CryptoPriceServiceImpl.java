package com.example.trading.service.impl;

import com.example.trading.enums.CryptoEnums;
import com.example.trading.model.entity.CryptoPrice;
import com.example.trading.model.response.CryptoBestAggregatePriceResponse;
import com.example.trading.repository.CryptoPriceRepository;
import com.example.trading.service.CryptoPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CryptoPriceServiceImpl implements CryptoPriceService {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Override
    public List<CryptoBestAggregatePriceResponse> findLatestBestAggregatedPrices() {
        log.info("CryptoPriceServiceImpl ==> findLatestBestAggregatedPrices");
        Pageable pageable = PageRequest.of(0, 2);
        List<CryptoPrice> priceEntities = cryptoPriceRepository.findLatestPrices(CryptoEnums.getKeys(), pageable);
        return priceEntities.stream()
                .map(i -> {
                    CryptoBestAggregatePriceResponse response = new CryptoBestAggregatePriceResponse();
                    BeanUtils.copyProperties(i, response);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
