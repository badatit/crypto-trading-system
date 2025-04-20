package com.example.trading.repository;

import com.example.trading.model.entity.CryptoPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoPriceRepository extends JpaRepository<CryptoPriceEntity, Long> {
}
