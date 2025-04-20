package com.example.trading.controller;

import com.example.trading.model.request.TradeRequest;
import com.example.trading.model.response.CryptoBestAggregatePriceResponse;
import com.example.trading.model.response.TradeResponse;
import com.example.trading.model.response.TradingHistoryResponse;
import com.example.trading.model.response.UserWalletResponse;
import com.example.trading.service.CryptoPriceService;
import com.example.trading.service.TradingService;
import com.example.trading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trade")
public class CryptoPriceController {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Autowired
    private TradingService tradingService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/prices/best")
    public ResponseEntity<List<CryptoBestAggregatePriceResponse>> getLatestBestAggregatedPrices() {
        return ResponseEntity.ok(cryptoPriceService.findLatestBestAggregatedPrices());
    }

    @PostMapping("/orders")
    public ResponseEntity<TradeResponse> getTradingOrder(@RequestBody TradeRequest request) {
        return ResponseEntity.ok(tradingService.handleTradeCryptoOrder(request));
    }

    @GetMapping("/wallet/{userId}")
    public ResponseEntity<UserWalletResponse> getWalletBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWalletByUserId(userId));
    }

    @GetMapping("/users/{userId}/history")
    public ResponseEntity<List<TradingHistoryResponse>> getTradingHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(tradingService.getUserTradingHistory(userId));
    }
}
