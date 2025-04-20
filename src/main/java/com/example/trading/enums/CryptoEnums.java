package com.example.trading.enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum CryptoEnums {
    ETHUSDT("Ethereum"),
    BTCUSDT("Bitcoin");

    private final String value;

    CryptoEnums(String value) {
        this.value = value;
    }

    public static List<String> getKeys() {
        List<String> keys = new ArrayList<>();
        for (CryptoEnums pair : CryptoEnums.values()) {
            keys.add(pair.name());
        }
        return keys;
    }
}
