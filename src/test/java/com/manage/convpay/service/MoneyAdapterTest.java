package com.manage.convpay.service;

import com.manage.convpay.type.MoneyUseCancelResult;
import com.manage.convpay.type.MoneyUseResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyAdapterTest {
    MoneyAdapter moneyAdapter = new MoneyAdapter();

    @Test
    void money_use_fail() {
        //given
        Integer payAmount = 1000_001;

        //when
        MoneyUseResult moneyUseResult = moneyAdapter.use(payAmount);

        //then
        Assertions.assertEquals(MoneyUseResult.USE_FAIL, moneyUseResult);
    }

    @Test
    void money_use_success() {
        //given
        Integer payAmount = 1000_000;

        //when
        MoneyUseResult moneyUseResult = moneyAdapter.use(payAmount);

        //then
        Assertions.assertEquals(MoneyUseResult.USE_SUCCESS, moneyUseResult);
    }

    @Test
    void money_use_cancel_success() {
        //given
        Integer payCancelAmount = 101;

        //when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);

        //then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_SUCCESS, moneyUseCancelResult);
    }

    @Test
    void money_use_cancel_fail() {
        //given
        Integer payCancelAmount = 99;

        //when
        MoneyUseCancelResult moneyUseCancelResult = moneyAdapter.useCancel(payCancelAmount);

        //then
        assertEquals(MoneyUseCancelResult.MONEY_USE_CANCEL_FAIL, moneyUseCancelResult);
    }
}