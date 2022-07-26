package com.manage.convpay.service;

import com.manage.convpay.dto.TransactionDto;
import com.manage.convpay.repository.TransactionRepository;
import com.manage.convpay.type.TransactionResultType;
import com.manage.convpay.type.TransactionType;
import com.manage.convpay.domain.Account;
import com.manage.convpay.domain.AccountUser;
import com.manage.convpay.domain.Transaction;
import com.manage.convpay.exception.AccountException;
import com.manage.convpay.repository.AccountRepository;
import com.manage.convpay.repository.AccountUserRepository;
import com.manage.convpay.type.AccountStatus;
import com.manage.convpay.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository; // jpa repository
    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public TransactionDto useBalance(Long userId,
                                     String accountNumber,
                                     Long amount){
        AccountUser user = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateUseBalance(user, account, amount);

        account.useBalance(amount);

        return TransactionDto.fromEntity(saveAndGetTransaction(
                TransactionType.USE,
                TransactionResultType.S,
                account,
                amount
        ));
    }

    private void validateUseBalance(AccountUser user, Account account, Long amount) {
        if(!Objects.equals(user.getId(), account.getAccountUser().getId())){
            throw new AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }
        if(account.getAccountStatus() != AccountStatus.IN_USE){
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }
        if(account.getBalance() < amount){
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
        if(amount >= 1_000_000_000 || amount < 1){
            throw new AccountException(ErrorCode.AMOUNT_BALANCE_IS_BIG_OR_SMALL);
        }
    }

    @Transactional
    public void saveFailedUseTransaction(String accountNumber, Long amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        saveAndGetTransaction(TransactionType.USE, TransactionResultType.F, account, amount);
    }

    private Transaction saveAndGetTransaction(
            TransactionType transactionType,
            TransactionResultType transactionResultType,
            Account account,
            Long amount) {
        return transactionRepository.save(
                Transaction.builder()
                        .transactionType(transactionType)
                        .transactionResultType(transactionResultType)
                        .account(account)
                        .amount(amount)
                        .balanceSnapshot(account.getBalance())
                        .transactionId(UUID.randomUUID().toString().replace("-", ""))
                        .transactedAt(LocalDateTime.now())
                        .build()
        );
    }

    @Transactional
    public TransactionDto cancelBalance(
            String transactionId,
            String accountNumber,
            Long amount
    ){
        Transaction transaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateCancelBalance(transaction, account, amount);

        account.cancelBalance(amount);

        return TransactionDto.fromEntity(
                saveAndGetTransaction(TransactionType.CANCEL, TransactionResultType.S, account, amount)
        );
    }

    private void validateCancelBalance(Transaction transaction, Account account, Long amount) {
        if(!Objects.equals(transaction.getAccount().getId(), account.getId())){
            throw new AccountException(ErrorCode.TRANSACTION_ACCOUNT_UN_MATCH);
        }
        if(!Objects.equals(transaction.getAmount(), amount)){
            throw new AccountException(ErrorCode.CANCEL_MUST_FULLY);
        }
        if(transaction.getTransactedAt().isBefore(LocalDateTime.now().minusYears(1))){
            throw new AccountException(ErrorCode.TOO_OLD_ORDER_TO_CANCEL);
        }
    }

    @Transactional
    public void saveFailedCancelTransaction(String accountNumber, Long amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
        saveAndGetTransaction(TransactionType.CANCEL, TransactionResultType.F, account, amount);
    }

    public TransactionDto queryTransaction(String transactionId) {
        return TransactionDto.fromEntity(
                transactionRepository.findByTransactionId(transactionId)
                    .orElseThrow(() -> new AccountException(ErrorCode.TRANSACTION_NOT_FOUND))
        );
    }
}
