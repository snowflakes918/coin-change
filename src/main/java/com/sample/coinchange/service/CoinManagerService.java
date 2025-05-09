package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class CoinManagerService {

    private final Map<CoinType, Integer> coinInventory;


    public CoinManagerService(@Value("${coin.quantity}") int coinQuantity) {
        // Initialize with 100 of each coins
        coinInventory = new ConcurrentHashMap<>();
        for (CoinType coinType : CoinType.values()) {
            coinInventory.put(coinType, coinQuantity);
        }
    }

    public Map<CoinType, Integer> getAvailableCoins() {
        return new ConcurrentHashMap<>(coinInventory);
    }

    public synchronized boolean hasSufficientCoins(Map<CoinType, Integer> requiredCoins) {
        for (Map.Entry<CoinType, Integer> entry : requiredCoins.entrySet()) {
            CoinType coinType = entry.getKey();
            int requiredCount = entry.getValue();
            if (coinInventory.getOrDefault(coinType, 0) < requiredCount) {
                return false;
            }
        }
        return true;
    }

    public synchronized void deductCoins(Map<CoinType, Integer> coinsToDeduct) {
        for (Map.Entry<CoinType, Integer> entry : coinsToDeduct.entrySet()) {
            CoinType coinType = entry.getKey();
            int countToDeduct = entry.getValue();
            coinInventory.put(coinType, coinInventory.get(coinType) - countToDeduct);
        }
    }

}