package com.example.trading.repository;

import com.example.trading.model.entity.UserWallet;
import com.example.trading.model.response.UserWalletResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    Optional<UserWallet> findByUserId(Long userId);
}
