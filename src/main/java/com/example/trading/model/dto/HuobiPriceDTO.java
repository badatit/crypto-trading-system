package com.example.trading.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HuobiPriceDTO {
    private List<HuobiPriceData> data;

    @Getter
    @Setter
    public static class HuobiPriceData {
        private String symbol;
        private double open;
        private double high;
        private double low;
        private double close;
        private double amount;
        private double vol;
        private int count;
        private double bid;
        private double bidSize;
        private double ask;
        private double askSize;
    }
}
