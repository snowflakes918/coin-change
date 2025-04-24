package com.sample.coinchange.controller;

import com.sample.coinchange.dto.CoinType;
import com.sample.coinchange.dto.ResponseType;
import com.sample.coinchange.dto.ResultEnum;
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

import static com.sample.coinchange.dto.ResponseType.success;

@RestController
public class CoinChangeController {

    @Autowired
    private ChangeStrategyService changeStrategyService;
    @Autowired
    private CoinManagerService coinManagerService;

    @GetMapping(value = "/api/change/{bill}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseType<Map<CoinType, Integer>> calculateChange(@PathVariable Integer bill) {
        Map<CoinType, Integer> changeMap = changeStrategyService.calculateChange(bill);
        return ResponseType.success(changeMap);
    }

    @GetMapping(value = "/api/coins",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseType<Map<CoinType, Integer>> getAvailableCoins() {
        Map<CoinType, Integer> availableCoins = coinManagerService.getAvailableCoins();
        return ResponseType.success(availableCoins);
    }
}
