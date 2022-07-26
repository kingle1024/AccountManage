package com.manage.convpay.controller;

import com.manage.convpay.domain.Account;
import com.manage.convpay.dto.AccountInfo;
import com.manage.convpay.dto.DeleteAccount;
import com.manage.convpay.service.AccountService;
import com.manage.convpay.dto.CreateAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
//    private final RedisTestSevice redisTestSevice;
//    @GetMapping("/get-lock")
//    public String getLock(){
//        return redisTestSevice.getLock();
//    }

    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
    ){
        return CreateAccount.Response.from(
            accountService.createAccount(
                request.getUserId(),
                request.getInitialBalance()
            )
        );
    }

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ){
        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ){
        // list를 받아서, collect로 AccountInfo를 만들어준다.
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDto ->
                        AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());
    }

    // 외부 -> 컨트롤러로만 접속 > 서비스 -> 레파지토리
//    @GetMapping("/create-account")
//    public String createAccountGet(){
//        accountService.createAccount();
//        return "success";
//    }
    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccount(id);
    }
}