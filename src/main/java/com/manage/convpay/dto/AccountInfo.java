package com.manage.convpay.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {
    private String accountNumber;
    private Long balance;

    // DTO를 정해진 용도로 만들지 않으면 나중에는 꼭 복잡한 상황이 생기고, 그런 상황이 생기다보면 의도치 않은 동작들이 하여 장애들이 발생했다.

}
