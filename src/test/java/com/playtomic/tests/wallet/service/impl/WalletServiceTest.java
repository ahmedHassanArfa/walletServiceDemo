package com.playtomic.tests.wallet.service.impl;


import com.playtomic.tests.wallet.dto.WalletDto;
import com.playtomic.tests.wallet.entity.Wallet;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.math.BigDecimal;
import java.net.URI;

@SpringBootTest
public class WalletServiceTest {

    @Autowired
    WalletService walletService;

    @Test
    public void testCreateWallet() throws Exception {
        WalletDto walletDto = new WalletDto();
        walletDto.setAccountNumber(Long.valueOf("102010"));
        walletDto.setCreditCardNumber("a12e12w45");
        walletDto = walletService.createWallet(walletDto);
        assert walletDto.getId() != null;
    }

    @Test
    public void testGetWallet() throws Exception {
        WalletDto walletDto = walletService.retrieveWallet("621a11961d93f93304add28c");
        assert walletDto != null && walletDto.getCreditCardNumber() != null;
    }

    @Test
    public void testChargeWallet() throws Exception {
        WalletDto walletDto = walletService.retrieveWallet("621a10c64b88ea166bf03236");
        BigDecimal oldValue = walletDto.getAmount();
        walletDto = walletService.chargeWallet("a12e12w45", BigDecimal.valueOf(15));
        assert walletDto.getAmount() == oldValue.add(BigDecimal.valueOf(15));
    }


}
