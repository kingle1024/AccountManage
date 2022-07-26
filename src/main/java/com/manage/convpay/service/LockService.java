package com.manage.convpay.service;

import com.manage.convpay.exception.AccountException;
import com.manage.convpay.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedissonClient redissonClient; // bean name == this name!

    public void lock(String accountNumber){
        RLock lock = redissonClient.getLock(getLockKey(accountNumber));
        log.debug("Trying lock for accntNumber : {}", accountNumber);

        try{
            boolean isLock = lock.tryLock(1, 5, TimeUnit.SECONDS);
            if(!isLock){
                log.error("==================Lock acquistiofailed ==============");
                throw new AccountException(ErrorCode.ACCOUNT_TRANSACTION_LOCK);
            }
        }catch(AccountException e){
            throw e;
        }catch(Exception e){
            log.error("Redis lock failed");
        }
    }

    public void unlock(String accountNumber){
        log.debug("Unlock for accountNumber : {} ", accountNumber);
        redissonClient.getLock(getLockKey(accountNumber)).unlock();
    }

    private String getLockKey(String accountNumber) {
        return "ACLK:" + accountNumber;
    }
}
