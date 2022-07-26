package com.manage.convpay.service;

import com.manage.convpay.dto.PayRequest;
import com.manage.convpay.type.ConvenienceType;
import com.manage.convpay.type.PayMethodType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountByConvenienceTest {
    DiscountByConvenience discountByConvenience = new DiscountByConvenience();

    @Test
    void discountTest() {
        //given
        PayRequest payRequestG25 =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.G25, 1000);
        PayRequest payRequestGU =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.GU, 1000);
        PayRequest payRequestSeven =
                new PayRequest(PayMethodType.MONEY, ConvenienceType.SEVEN, 1000);

        //when
        Integer discountedAmountG25 = discountByConvenience.getDiscountedAmount(payRequestG25);
        Integer discountedAmountGU = discountByConvenience.getDiscountedAmount(payRequestGU);
        Integer discountedAmountSeven = discountByConvenience.getDiscountedAmount(payRequestSeven);

        //then
        assertEquals(800, discountedAmountG25);
        assertEquals(900, discountedAmountGU);
        assertEquals(1000, discountedAmountSeven);
    }

}