package com.example.trading.service.impl;

import com.example.trading.exception.InvalidInputException;
import com.example.trading.exception.NotFoundException;
import com.example.trading.model.entity.CryptoPrice;
import com.example.trading.model.entity.TradeTransaction;
import com.example.trading.model.entity.UserWallet;
import com.example.trading.model.request.TradeRequest;
import com.example.trading.model.response.TradeResponse;
import com.example.trading.model.response.TradingHistoryResponse;
import com.example.trading.repository.CryptoPriceRepository;
import com.example.trading.repository.TradeTransactionRepository;
import com.example.trading.repository.UserWalletRepository;
import com.example.trading.service.TradingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TradingServiceImpl implements TradingService {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;

    @Autowired
    private UserWalletRepository userWalletRepository;

    @Autowired
    private TradeTransactionRepository tradeTransactionRepository;

    @Override
    @Transactional
    public TradeResponse handleTradeCryptoOrder(TradeRequest request) {
        String symbol = request.getSymbol().toUpperCase();
        String orderType = request.getOrderType().toUpperCase();
        Double amount = request.getAmount();
        double matchedPrice = 0.0;
        try {
            CryptoPrice price = cryptoPriceRepository.findTopBySymbolOrderByCreatedAtDesc(symbol)
                    .orElseThrow(() -> new NotFoundException("Price not available"));

            UserWallet wallet = userWalletRepository.findById(request.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));

            switch (orderType) {
                case "BUY":
                    matchedPrice = this.handleBuy(amount, price, wallet, symbol);
                    break;
                case "SELL":
                    matchedPrice = this.handleSell(amount, price, symbol, wallet);
                    break;
                default:
                    throw new RuntimeException("Invalid order type: " + orderType);
            }
            userWalletRepository.save(wallet);
        } catch (Exception e) {
            log.error("Error with handleTradeCryptoOrder- {} ", e.getMessage());
        }

        TradeTransaction trade = new TradeTransaction();
        trade.setUserId(request.getUserId());
        trade.setOrderType(orderType);
        trade.setSymbol(symbol);
        trade.setAmount(amount);
        trade.setPrice(matchedPrice);
        trade.setStatus("Success");
        tradeTransactionRepository.save(trade);
        return new TradeResponse("Success", "Trade ", trade.getId());
    }

    private double handleSell(Double amount, CryptoPrice price, String symbol, UserWallet wallet) {
        if (amount > price.getBestBidQty()) {
            throw new InvalidInputException("Not enough market liquidity to SELL");
        }

        double matchedPrice = price.getBestBid();
        double totalCost = amount * matchedPrice;

        if (symbol.startsWith("BTC") && wallet.getBtcBalance() < amount) {
            throw new RuntimeException("Not enough BTC to sell");
        } else if (symbol.startsWith("ETH") && wallet.getEthBalance() < amount) {
            throw new RuntimeException("Not enough ETH to sell");
        }

        wallet.setBalance(wallet.getBalance() + totalCost);
        if (symbol.startsWith("BTC")) {
            wallet.setBtcBalance(wallet.getBtcBalance() - amount);
        } else if (symbol.startsWith("ETH")) {
            wallet.setEthBalance(wallet.getEthBalance() - amount);
        }
        return matchedPrice;
    }

    private double handleBuy(Double amount, CryptoPrice price, UserWallet wallet, String symbol) {
        if (amount > price.getBestAskQty()) {
            throw new InvalidInputException("Not enough market liquidity to BUY");
        }

        double matchedPrice = price.getBestAsk();
        double totalCost = amount * matchedPrice;

        if (wallet.getBalance() < totalCost) {
            throw new InvalidInputException("Not enough USDT in wallet");
        }

        wallet.setBalance(wallet.getBalance() - totalCost);
        if (symbol.startsWith("BTC")) {
            wallet.setBtcBalance(wallet.getBtcBalance() + amount);
        } else if (symbol.startsWith("ETH")) {
            wallet.setEthBalance(wallet.getEthBalance() + amount);
        }
        return matchedPrice;
    }

    @Override
    public List<TradingHistoryResponse> getUserTradingHistory(Long userId) {
        List<TradeTransaction> tradeTransactions = tradeTransactionRepository.findByUserId(userId)
                .orElse(Collections.emptyList());
        return tradeTransactions.stream().map(i -> {
                    TradingHistoryResponse response = new TradingHistoryResponse();
                    BeanUtils.copyProperties(i, response);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
