package com.manage.convpay.service;

import com.manage.convpay.dto.PayCancelRequest;
import com.manage.convpay.dto.PayCancelResponse;
import com.manage.convpay.dto.PayRequest;
import com.manage.convpay.dto.PayResponse;
import com.manage.convpay.type.ConvenienceType;
import com.manage.convpay.type.PayCancelResult;
import com.manage.convpay.type.PayMethodType;
import com.manage.convpay.type.PayResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ConveniencePayServiceTest {
    ConveniencePayService conveniencePayService = new ConveniencePayService(
            new HashSet<>(
                    Arrays.asList(new MoneyAdapter(), new CardAdapter())
            ),
            new DiscountByConvenience()
    );

    @Test
    void pay_success() {
        //given
        PayRequest payRequest = new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 50);

        //when
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        //then
        Assertions.assertEquals(PayResult.SUCCESS, payResponse.getPayResult());
        assertEquals(40, payResponse.getPaidAmount());
    }

    @Test
    void pay_fail() {
        //given
        PayRequest payRequest = new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 1500_001);

        //when
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        //then
        assertEquals(PayResult.FAIL, payResponse.getPayResult());
        assertEquals(0, payResponse.getPaidAmount());
    }

    @Test
    void pay_cancel_success() {
        //given
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY, ConvenienceType.G25, 1000);

        //when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        //then
        Assertions.assertEquals(PayCancelResult.PAY_CANCEL_SUCCESS, payCancelResponse.getPayCancelResult());
        assertEquals(1000, payCancelResponse.getPayCanceledAmount());
    }

    @Test
    void pay_cancel_fail() {
        //given
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY, ConvenienceType.G25, 99);

        //when
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        //then
        Assertions.assertEquals(PayCancelResult.PAY_CANCEL_FAIL, payCancelResponse.getPayCancelResult());
        assertEquals(0, payCancelResponse.getPayCanceledAmount());
    }
}