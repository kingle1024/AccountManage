# 설명
계좌 관리 시스템을 SpringBoot + JPA로 만드는 프로젝트입니다.<br>
계좌를 생성하고, 해지하고, 확인하는 기능이 있으며<br>
계좌에 있는 금액을 사용하고, 취소하면서 발생한 거래를 확인하는 기능이 있습니다. 


# 구현 기능
계좌(Account) 관련 API 
- 계좌 생성(@Post /account)
- 계좌 해지(@Delete /account)
- 계좌 확인(@get /account)

거래(Transaction) 관련 API
- 잔액 사용(@Post /transaction/use)
- 잔액 사용 취소(@Post /transaction/cancel)
- 거래 확인(@@Get /transaction/{transactionId})

# 기술 스택
- SpringBoot : 2.6.4
- JDK : 11
- JPA
- lombok : 1.18
- slf4j
- redisson : 3.17.1
