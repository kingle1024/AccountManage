package com.manage.convpay.dto;

import com.manage.convpay.domain.Account;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    private Long userId;
    private String accountNumber;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public AccountDto(Long userId){
        this.userId = userId;
    }

    public static AccountDto fromEntity(Account account){
        // 생성자를 쓰는 것도 괜찮겠지만, DTO는 특히나 Entity를 통해서 만들어지는 경우가 가장 많다.
        // 그러다보니깐 entity를 갖고 dto로 변환해줄 수 있게 static메소드를 만들어주는게
        // 가독성도 좋고 훨씬 안전하게 생성할 수 있다.
        return AccountDto.builder()
                .userId(account.getAccountUser().getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .registeredAt(account.getRegisteredAt())
                .unRegisteredAt(account.getUnRegisteredAt())
                .build();
    }
}