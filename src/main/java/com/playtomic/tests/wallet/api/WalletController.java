package com.playtomic.tests.wallet.api;

import com.playtomic.tests.wallet.dto.WalletDto;
import com.playtomic.tests.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("wallet")
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletService walletService;

    @RequestMapping("/")
    void log() {
        log.info("Logging from /");
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable String id) throws Exception {
        WalletDto walletDto = walletService.retrieveWallet(id);
        return ResponseEntity.ok(walletDto);
    }

    @PutMapping("/charging")
    public ResponseEntity chargeWallet(@RequestParam(required = true) String creditCardNumber, @RequestParam(required = true) BigDecimal amount) throws Exception {
        walletService.chargeWallet(creditCardNumber, amount);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<WalletDto> createWallet(@RequestBody WalletDto walletDto) {
        walletDto = walletService.createWallet(walletDto);
        return ResponseEntity.ok(walletDto);
    }

    //TODO purchase Api

    //TODO refund Api

}
