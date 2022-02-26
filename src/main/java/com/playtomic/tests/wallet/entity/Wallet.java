package com.playtomic.tests.wallet.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Document(collation = "wallet")
public class Wallet implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String creditCardNumber;

    private BigDecimal amount;

    private Long accountNumber;

}
