package com.manage.convpay.controller;

import com.manage.convpay.aop.AccountLock;
import com.manage.convpay.dto.CancelBalance;
import com.manage.convpay.dto.QueryTransactionResponse;
import com.manage.convpay.dto.UseBalance;
import com.manage.convpay.service.TransactionService;
import com.manage.convpay.exception.AccountException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 *   1.
 *   2.
 *   3.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction/use")
    @AccountLock
    public UseBalance.Response useBalance(
            @Valid @RequestBody UseBalance.Request request
    ){
        try {
            return UseBalance.Response.from(
                    transactionService.useBalance(
                            request.getUserId(),
                            request.getAccountNumber(),
                            request.getAmount())
            );
        }catch(AccountException e){
            log.error("Failed to use balance. ");
            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );
            throw e;
        }
    }
    @PostMapping("/transaction/cancel")
    @AccountLock
    public CancelBalance.Response cancelBalance(
       @Valid @RequestBody CancelBalance.Request request
    ){
        try{
            return CancelBalance.Response.from(
                    transactionService.cancelBalance(
                            request.getTransactionId(),
                            request.getAccountNumber(),
                            request.getAmount())
            );
        }catch(AccountException e){
            log.error("Failed to use balance.");
            transactionService.saveFailedCancelTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public QueryTransactionResponse queryTransaction(
        @PathVariable String transactionId
    ){
        return QueryTransactionResponse.from(
            transactionService.queryTransaction(transactionId)
        );
    }

}
