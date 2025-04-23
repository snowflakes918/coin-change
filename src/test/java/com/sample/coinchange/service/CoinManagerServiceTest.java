package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoinManagerServiceTest {

    private CoinManagerService coinManagerService;

    @BeforeEach
    void setUp() {
        coinManagerService = new CoinManagerService(100);
    }

    @Test
    void coinInventoryInitTest() {
        // Act
        Map<CoinType, Integer> inventory = coinManagerService.getAvailableCoins();

        assertEquals(4, inventory.size());
        assertEquals(100, inventory.get(CoinType.PENNY));
        assertEquals(100, inventory.get(CoinType.NICKEL));
        assertEquals(100, inventory.get(CoinType.DIME));
        assertEquals(100, inventory.get(CoinType.QUARTER));
    }

    @Test
    void hasSufficientCoinsTest() {
        // Scenario 1: Sufficient coins
        Map<CoinType, Integer> requiredCoins = Map.of(CoinType.QUARTER, 50);
        // Scenario 2: Insufficient coins
        Map<CoinType, Integer> insufficientCoins = Map.of(CoinType.QUARTER, 101);


        assertTrue(coinManagerService.hasSufficientCoins(requiredCoins));
        assertFalse(coinManagerService.hasSufficientCoins(insufficientCoins));


    }

    @Test
    void deductCoinsTest() {

        Map<CoinType, Integer> coinsToDeduct = Map.of(CoinType.DIME, 10);
        coinManagerService.deductCoins(coinsToDeduct);

        // Assert
        assertEquals(90, coinManagerService.getCoinInventory().get(CoinType.DIME));
    }

    @Test
    void getMaxAmountTest() {
        int maxAmount = coinManagerService.getMaxAmount();

        // Assert
        assertEquals(41, maxAmount);
    }
}
