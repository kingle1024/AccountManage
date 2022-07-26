package com.manage.convpay.service;

import com.manage.convpay.dto.PayRequest;
import com.manage.convpay.type.ConvenienceType;

import static com.manage.convpay.type.ConvenienceType.*;

public class DiscountByConvenience implements DiscountInterface{
    @Override
    public Integer getDiscountedAmount(PayRequest payRequest) {
        switch (payRequest.getConvenienceType()) {
            case G25:
                return payRequest.getPayAmount() * 8 / 10;
            case GU:
                return payRequest.getPayAmount() * 9 / 10;
            case SEVEN:
                return payRequest.getPayAmount();
        }

        return payRequest.getPayAmount();
    }
}
