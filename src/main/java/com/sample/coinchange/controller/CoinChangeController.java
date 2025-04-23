package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.service.ChangeStrategyService;
import com.sample.coinchange.service.CoinManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class CoinChangeController {

    @Autowired
    private ChangeStrategyService changeStrategyService;
    @Autowired
    private CoinManagerService coinManagerService;

    @GetMapping(value = "/api/change/{bill}",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<CoinType, Integer> calculateChange(@PathVariable Integer bill) {
        return changeStrategyService.calculateChange(bill);
    }

    @GetMapping(value = "/api/coins",
                produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<CoinType, Integer> getAvailableCoins() {
        return coinManagerService.getAvailableCoins();
    }
}
