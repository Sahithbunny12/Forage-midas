package com.jpmc.midascore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jpmc.midascore.foundation.Balance;

@RestController
public class BalanceController {

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        return new Balance(0);
    }
}
