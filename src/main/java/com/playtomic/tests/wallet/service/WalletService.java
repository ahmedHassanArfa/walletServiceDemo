package com.playtomic.tests.wallet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.dto.WalletDto;
import com.playtomic.tests.wallet.entity.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {

    private final StripeService stripeService;
    private final MongoTemplate mongoTemplate;

    public WalletService(StripeService stripeService, MongoTemplate mongoTemplate) {
        this.stripeService = stripeService;
        this.mongoTemplate = mongoTemplate;
    }

    public WalletDto retrieveWallet(String id) throws Exception {
        Wallet wallet = mongoTemplate.findById(id, Wallet.class);
        ObjectMapper objectMapper = new ObjectMapper();
        WalletDto walletDto = objectMapper.convertValue(wallet, WalletDto.class);
        return walletDto;
    }

    @Transactional
    public WalletDto chargeWallet(@NonNull String creditCardNumber, @NonNull BigDecimal amount) throws Exception {
        WalletDto walletDto = null;
        stripeService.charge(creditCardNumber, amount);
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("creditCardNumber").is(creditCardNumber));
            Update update = new Update();
            update.inc("amount", amount);
            // update by increment value as atomic operation
            Wallet wallet = mongoTemplate.findAndModify(query, update, Wallet.class);
            ObjectMapper objectMapper = new ObjectMapper();
            walletDto = objectMapper.convertValue(wallet, WalletDto.class);
        } catch (Exception e) {
            //TODO subtract amount in case charge succeed and save in db failed
        }
        return walletDto;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleRollback(final Object event) {
        // maybe we can use it in case there is custom rollback we need to do
    }

    @Transactional
    public WalletDto createWallet(WalletDto walletDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        Wallet wallet = objectMapper.convertValue(walletDto, Wallet.class);
        wallet = mongoTemplate.save(wallet);
        return objectMapper.convertValue(wallet, WalletDto.class);
    }



}
