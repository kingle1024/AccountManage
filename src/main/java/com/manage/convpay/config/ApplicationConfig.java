package com.manage.convpay.config;

import com.manage.convpay.service.CardAdapter;
import com.manage.convpay.service.ConveniencePayService;
import com.manage.convpay.service.DiscountByConvenience;
import com.manage.convpay.service.MoneyAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class ApplicationConfig {

    @Bean
    public ConveniencePayService conveniencePayService(){
        return new ConveniencePayService(
                new HashSet<>(Arrays.asList(moneyAdapter(), cardAdapter())),
                discountByConvenience()
        );
    }

    @Bean
    public CardAdapter cardAdapter() {
        return new CardAdapter();
    }

    @Bean
    public MoneyAdapter moneyAdapter() {
        return new MoneyAdapter();
    }

    @Bean
    public DiscountByConvenience discountByConvenience() {
        return new DiscountByConvenience();
    }
}
