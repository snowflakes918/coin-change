package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

@Service
public class ChangeStrategyService {

    private final CoinManagerService coinManagerService;

    public ChangeStrategyService(CoinManagerService coinManagerService) {
        this.coinManagerService = coinManagerService;
    }


    public synchronized Map<CoinType, Integer> calculateChange(int bill) {
        // Validate the bill amount
        if (!isValidBill(bill)) {
            throw new IllegalArgumentException("Invalid bill amount: " + bill);
        }

        BigDecimal remainingBill = BigDecimal.valueOf(bill);
        Map<CoinType, Integer> tempDeduction = new EnumMap<>(CoinType.class);

        // Sort coin types by their value in descending order
        CoinType[] coinTypes = CoinType.values();
        Arrays.sort(coinTypes, (CoinType a, CoinType b) -> b.getAmount().compareTo(a.getAmount()));

        // Use greedy method to calculate change
        for (CoinType coinType : coinTypes) {
            int coinUsed = 0;
            BigDecimal coinValue = coinType.getAmount();

            while (remainingBill.compareTo(coinValue) >= 0 &&
                    coinManagerService.hasSufficientCoins(Map.of(coinType, coinUsed+1))) {
                remainingBill = remainingBill.subtract(coinValue);
                coinUsed++;
            }

            if (coinUsed > 0) {
                tempDeduction.put(coinType, coinUsed);
            }

        }

        // There aren't enough coins
        if (remainingBill.compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalStateException("Insufficient coins to provide change.");
        }

        coinManagerService.deductCoins(tempDeduction);
        return tempDeduction;
    }

    private boolean isValidBill(int bill) {
        return bill == 1 || bill == 2 || bill == 5 || bill == 10 || bill == 20 || bill == 50 || bill == 100;
    }
}
