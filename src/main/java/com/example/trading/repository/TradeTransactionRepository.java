package com.example.trading.repository;

import com.example.trading.model.entity.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {

    Optional<List<TradeTransaction>> findByUserId(Long userId);
}
