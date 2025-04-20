package com.example.trading.service.impl;

import com.example.trading.exception.NotFoundException;
import com.example.trading.model.entity.UserWallet;
import com.example.trading.model.response.UserWalletResponse;
import com.example.trading.repository.UserWalletRepository;
import com.example.trading.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private UserWalletRepository userWalletRepository;

    @Override
    public UserWalletResponse getWalletByUserId(Long userId) {
        log.info("WalletServiceImpl ==> getWalletByUserId: userId={}", userId);
        UserWallet userWallet = userWalletRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User wallet not found for userId: " + userId));
        UserWalletResponse response = new UserWalletResponse();
        BeanUtils.copyProperties(userWallet, response);
        return response;
    }
}
