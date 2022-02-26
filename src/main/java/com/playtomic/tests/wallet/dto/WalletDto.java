package com.playtomic.tests.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletDto implements Serializable {

    private String id;

    @NonNull
    private String creditCardNumber;

    private BigDecimal amount = BigDecimal.ZERO;

    private Long accountNumber;

}
