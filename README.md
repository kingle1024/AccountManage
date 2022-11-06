# 제작 기간
2022.07.12 ~ 07.26

# 설명
계좌 관리 시스템을 SpringBoot + JPA로 만드는 프로젝트입니다.<br>
계좌를 생성하고, 해지하고, 확인하는 기능이 있으며<br>
계좌에 있는 금액을 사용하고, 취소하면서 발생한 거래를 확인하는 기능이 있습니다. 


# 구현 기능
[계좌(Account) 관련 API]
- 계좌 생성(@Post /account)
- 계좌 해지(@Delete /account)
- 계좌 확인(@Get /account)

[거래(Transaction) 관련 API]
- 잔액 사용(@Post /transaction/use)
- 잔액 사용 취소(@Post /transaction/cancel)
- 거래 확인(@Get /transaction/{transactionId})

# 주요 기술
- DI 구축으로 결제 수단 및 결제 대상에 대해 의존성 최소화
- @Transactional 사용하면서 작업 단위를 최소화
- @ExceptionHandler 활용하여 에러 예외 처리
- enum 활용하여 상태 코드 관리
- Redis 적용
- TDD 적용 (given, when, then)
- AOP를 사용하여 동시성 제어

# 기술 스택
- SpringBoot : 2.6.4
- JDK : 11
- JPA
- lombok : 1.18
- slf4j
- redisson : 3.17.1
