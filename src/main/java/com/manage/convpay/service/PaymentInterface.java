package com.manage.convpay.service;

import com.manage.convpay.type.CancelPaymentResult;
import com.manage.convpay.type.PayMethodType;
import com.manage.convpay.type.PaymentResult;

public interface PaymentInterface {
    PayMethodType getPayMethodType();
    PaymentResult payment(Integer payAmount);
    CancelPaymentResult cancelPayment(Integer cancelAmount);
}
