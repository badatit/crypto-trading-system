package com.example.trading.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AggregatedPriceDTO {
    private String source;
    private String symbol;
    private double bid;
    private double ask;

    public AggregatedPriceDTO() {}
}
