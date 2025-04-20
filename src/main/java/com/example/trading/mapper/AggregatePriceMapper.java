package com.example.trading.mapper;

import com.example.trading.model.dto.AggregatedPriceDTO;
import com.example.trading.model.dto.BinancePriceDTO;
import com.example.trading.model.dto.HuobiPriceDTO;
import org.springframework.stereotype.Component;

@Component
public class AggregatePriceMapper {

    public AggregatedPriceDTO mapToAggregatedPriceDTO(BinancePriceDTO binancePriceDTO) {
        AggregatedPriceDTO aggregatedPriceDTO = new AggregatedPriceDTO();
        aggregatedPriceDTO.setSource("Binance");
        aggregatedPriceDTO.setSymbol(binancePriceDTO.getSymbol());
        aggregatedPriceDTO.setAsk(binancePriceDTO.getAskPrice());
        aggregatedPriceDTO.setBid(binancePriceDTO.getBidPrice());
        return aggregatedPriceDTO;
    }

    public AggregatedPriceDTO mapToAggregatedPriceDTO(HuobiPriceDTO.HuobiPriceData huobiPriceData) {
        AggregatedPriceDTO aggregatedPriceDTO = new AggregatedPriceDTO();
        aggregatedPriceDTO.setSource("Huobi");
        aggregatedPriceDTO.setSymbol(huobiPriceData.getSymbol());
        aggregatedPriceDTO.setAsk(huobiPriceData.getAsk());
        aggregatedPriceDTO.setBid(huobiPriceData.getBid());
        return aggregatedPriceDTO;
    }
}
