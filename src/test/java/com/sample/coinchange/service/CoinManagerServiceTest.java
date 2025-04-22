package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoinManagerServiceTest {

    private CoinManagerService coinManagerService;

    @BeforeEach
    void setUp() {
        coinManagerService = new CoinManagerService();
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
        // Arrange
        Map<CoinType, Integer> requiredCoins = Map.of(CoinType.QUARTER, 50);

        // Act & Assert
        assertTrue(coinManagerService.hasSufficientCoins(requiredCoins));
    }

    @Test
    void deductCoinsTest() {
        // Arrange
        Map<CoinType, Integer> coinsToDeduct = Map.of(CoinType.DIME, 10);

        // Act
        coinManagerService.deductCoins(coinsToDeduct);

        // Assert
        assertEquals(90, coinManagerService.getCoinInventory().get(CoinType.DIME));
    }

    @Test
    void getMaxAmountTest() {
        // Act
        int maxAmount = coinManagerService.getMaxAmount();

        // Assert
        assertTrue(maxAmount > 0);
    }
}
