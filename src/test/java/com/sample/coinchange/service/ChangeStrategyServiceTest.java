package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeStrategyServiceTest {

    @Mock
    private CoinManagerService coinManagerService;

    @InjectMocks
    private ChangeStrategyService changeStrategyService;

    @Test
    void calculateChangeTest_with_one_cointype() {
        // Scenario 1: Change with 1 type of coin
        when(coinManagerService.getMaxAmount()).thenReturn(41);
        when(coinManagerService.hasSufficientCoins(any())).thenReturn(true);


        Map<CoinType, Integer> change = changeStrategyService.calculateChange(10);

        // Assert
        assertNotNull(change);
        assertEquals(1, change.size());
        assertEquals(40, change.get(CoinType.QUARTER));
    }

    @Test
    void calculateChangeTest_Invalid_Bill() {
        assertThrows(IllegalArgumentException.class,
                () -> changeStrategyService.calculateChange(12));
    }

    @Test
    void calculateChangeTest_Insufficient_Coins() {
        when(coinManagerService.getMaxAmount()).thenReturn(5);

        assertThrows(IllegalStateException.class, () -> changeStrategyService.calculateChange(10));
    }
}
