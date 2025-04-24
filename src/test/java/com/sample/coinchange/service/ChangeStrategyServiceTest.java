package com.sample.coinchange.service;

import com.sample.coinchange.dto.CoinType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
        assertThrows(IllegalStateException.class, () -> changeStrategyService.calculateChange(10));
    }

    @Test
    void calculateChangeTest_multiThreaded() throws InterruptedException {
        // Scenario: Multi-threaded access
        int numThreads = 10;
        CountDownLatch latch = new CountDownLatch(numThreads);
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger failureCount = new AtomicInteger(0);

        when(coinManagerService.hasSufficientCoins(any())).thenReturn(true);
        doNothing().when(coinManagerService).deductCoins(any());

        // Start multiple threads to simulate concurrent access, check how many thread could fail
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    Map<CoinType, Integer> result = changeStrategyService.calculateChange(5);
                    if (result != null && !result.isEmpty()) {
                        successCount.incrementAndGet();
                    }
                } catch (Exception e) {
                    failureCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        boolean completed = latch.await(5, TimeUnit.SECONDS);
        executorService.shutdown();

        assertTrue(completed);
        assertEquals(numThreads, successCount.get());
        assertEquals(0, failureCount.get());
        verify(coinManagerService, times(numThreads)).deductCoins(any());
    }
}
