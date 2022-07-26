package com.manage.convpay.service;

import com.manage.convpay.dto.PayRequest;

public interface DiscountInterface {
    Integer getDiscountedAmount(PayRequest payRequest);
}
