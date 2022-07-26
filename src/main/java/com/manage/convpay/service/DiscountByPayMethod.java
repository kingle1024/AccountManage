package com.manage.convpay.service;

import com.manage.convpay.dto.PayRequest;

public class DiscountByPayMethod implements DiscountInterface{
    @Override
    public Integer getDiscountedAmount(PayRequest payRequest) {
        switch (payRequest.getPayMethodType()){
            case MONEY:
                return payRequest.getPayAmount() * 7 / 10;
            case CARD:
                return payRequest.getPayAmount();
        }

        return payRequest.getPayAmount();
    }
}
