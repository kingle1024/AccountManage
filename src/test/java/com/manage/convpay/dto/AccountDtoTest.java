package com.manage.convpay.dto;

import org.junit.jupiter.api.Test;

class AccountDtoTest {
    @Test
    void accountDto(){
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountNumber("accountNumber");

        System.out.println(accountDto.getAccountNumber());
        System.out.println(accountDto.toString());
    }
}