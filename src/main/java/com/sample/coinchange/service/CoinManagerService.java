package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;

@Getter
@Service
public class CoinManagerService {

    private final Map<CoinType, Integer> coinInventory;

    public CoinManagerService() {
        // Initialize with 100 of each coins
        coinInventory = new EnumMap<>(CoinType.class);
        for (CoinType coinType : CoinType.values()) {
            coinInventory.put(coinType, 100);
        }
    }

    public Map<CoinType, Integer> getAvailableCoins() {
        return new EnumMap<>(coinInventory);
    }

    public boolean hasSufficientCoins(Map<CoinType, Integer> requiredCoins) {
        for (Map.Entry<CoinType, Integer> entry : requiredCoins.entrySet()) {
            CoinType coinType = entry.getKey();
            int requiredCount = entry.getValue();
            if (coinInventory.getOrDefault(coinType, 0) < requiredCount) {
                return false;
            }
        }
        return true;
    }

    public void deductCoins(Map<CoinType, Integer> coinsToDeduct) {
        for (Map.Entry<CoinType, Integer> entry : coinsToDeduct.entrySet()) {
            CoinType coinType = entry.getKey();
            int countToDeduct = entry.getValue();
            coinInventory.put(coinType, coinInventory.get(coinType) - countToDeduct);
        }
    }

    public int getMaxAmount() {
        return coinInventory.entrySet().stream()
                .mapToInt(entry ->
                        entry.getKey().getAmount()
                                .multiply(BigDecimal.valueOf(entry.getValue()))
                                .intValue()
                )
                .sum();
    }
}