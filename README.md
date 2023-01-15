## 회원관리 API 작성
```
1. 자세한 요청과 응답은 swagger를 통해 확인 부탁드립니다.
``` 

### 1. 개발환경
 - JDK11 
 - Spring Boot 2.7.8
 - Spring Security
 - Spring doc
 - JPA
 - H2 Embeded
### 2. 실행방법

### 3. API 공통
 - Request URL : http://{IP}:8080/v1/**
 - Swagger URL : http://{IP}:8080/swagger-ui/index.html
 - Content-Type : application/json
 
 - **공통응답 예시**
```
/* success 예시 */
{
  "code": "0000",                       
  "message": "Request Success",
  "data": {
      "accessToken" : "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2t5d3NAYWEuY29tIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTY3Mzc3MDIzNSwiZXhwIjoxNjczNzczODM1fQ.yYVnBDzftkDSMPPD_noJydxnX2-QoRb_LWPmNnYGF5M"
    }
}
/* error 예시 */
{
    "code": "-9999",
    "message": "Bad Request",
    "data": [
        {
            "fieldName" : "phNumber",
            "errorMessage" : "올바른 형식의 휴대전화번호를 입력해주세요."
        }
    ]
}
```
### 4. API 목록
 ##### 4.1. 회원가입 기능
 - PUT /v1/member/signup 회원가입 처리
 ```
 curl --location --request PUT 'http://localhost:8080/v1/member/signup' 
--header 'Content-Type: application/json' 
--data-raw '{
    "email" : "askyws@aa.com",
    "name" : "김세일즈",
    "nickName" : "닉넴",
    "phNumber" : "01038811393",
    "password" : "1q2w3e4r!"
}'
 ```
 - GET /v1/member/email/{email} 이메일중복검사
 ```
 curl --location --request GET 'http://localhost:8080/v1/member/email/askyws@aa.com'
 ```
 - POST /v1/member/phone/certify 휴대폰 번호 인증 발송
 ```
curl --location --request POST 'localhost:8080/v1/member/phone/certify' 
--header 'Content-Type: application/json' 
--data-raw '{
    "telecomCode" : "SKT",
    "phNumber" : "01038811393"
}'
 ```
- POST /v1/member/phone/verify 휴대폰 번호 인증 완료
 ```
curl --location --request POST 'localhost:8080/v1/member/phone/verify' 
--header 'Content-Type: application/json' 
--data-raw '{
    "phNumber" : "01038811393",
    "veriNumber" : "1234"
}'
 ```
 ##### 4.2. 로그인 기능
 - POST /v1/authentication 로그인

 이메일 + 패스워드 입력
 ```
 curl --location --request POST 'localhost:8080/v1/authentication'
--header 'Content-Type: application/json' 
--data-raw '{
    "username" : "askyws@aa.com",
    "password" : "1q2w3e4r!"
}'
 ```
 휴대전화번호 + 패스워드 입력
```
curl --location --request POST 'localhost:8080/v1/authentication'
--header 'Content-Type: application/json' 
--data-raw '{
   "username" : "01038811393",
   "password" : "1q2w3e4r!"
}'
```
 #### 4.3. 내정보 기능
 - GET /v1/my 내정보 조회
 ```
 curl --location --request GET 'localhost:8080/v1/my'
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2t5d3NAYWEuY29tIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTY3Mzc3MDIzNSwiZXhwIjoxNjczNzczODM1fQ.yYVnBDzftkDSMPPD_noJydxnX2-QoRb_LWPmNnYGF5M'
 ```
 #### 4.4. 패스워드 재설정
- POST /v1/member/phone/certify 휴대폰 번호 인증 발송
 ```
curl --location --request POST 'localhost:8080/v1/member/phone/certify' 
--header 'Content-Type: application/json' 
--data-raw '{
    "telecomCode" : "SKT",
    "phNumber" : "01038811393"
}'
 ```
- POST /v1/member/phone/verify 휴대폰 번호 인증 완료
 ```
curl --location --request POST 'localhost:8080/v1/member/phone/verify' 
--header 'Content-Type: application/json' 
--data-raw '{
    "phNumber" : "01038811393",
    "veriNumber" : "1234"
}'
 ```
 - PATCH /v1/member/password 패스워드 재설정
 ```
 curl --location --request PATCH 'localhost:8080/v1/member/password'
--header 'Content-Type: application/json'
--data-raw '{
    "rePassword" : "1q2w3e4r!!",
    "phNumber" : "0104454454"
}'
 ```
