package com.example.trading.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BinancePriceDTO {
    private String symbol;
    private double bidPrice;
    private double bidQty;
    private double askPrice;
    private double askQty;
}
