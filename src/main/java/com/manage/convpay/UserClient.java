package com.manage.convpay;

import com.manage.convpay.config.ApplicationConfig;
import com.manage.convpay.dto.PayCancelRequest;
import com.manage.convpay.dto.PayCancelResponse;
import com.manage.convpay.dto.PayRequest;
import com.manage.convpay.dto.PayResponse;
import com.manage.convpay.service.ConveniencePayService;
import com.manage.convpay.type.ConvenienceType;
import com.manage.convpay.type.PayMethodType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserClient {
    public static void main(String[] args) {
        // `사용자` -> 편결이 -> 머니

        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ConveniencePayService conveniencePayService =
                applicationContext.getBean("conveniencePayService",
                        ConveniencePayService.class);

        // G25, 결제 1000원
        PayRequest payRequest = new PayRequest(PayMethodType.CARD,
                ConvenienceType.G25, 50);
        PayResponse payResponse = conveniencePayService.pay(payRequest);

        System.out.println(payResponse);

        // G25, 취소 500원
        PayCancelRequest payCancelRequest = new PayCancelRequest(PayMethodType.MONEY,
                ConvenienceType.G25, 500);
        PayCancelResponse payCancelResponse = conveniencePayService.payCancel(payCancelRequest);

        System.out.println(payCancelResponse);
    }
}
