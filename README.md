# JWT-SECRET

## 사용자 회원가입 및 로그인 구현

Spring Security를 사용하여 JWT 필터를 통해 로그인을 구현했습니다.

---

## ERD

![erd](./docs/images/erd.png)

---

## API

[Swagger UI 로 접속하여 API 목록을 확인 할 수 있습니다.](http://54.180.221.241:8080/swagger-ui/index.html)

1. 회원가입 (POST /signup)
    - Request Message

       ```json
       {
           "username": "JIN HO",
           "password": "12341234",
           "nickname": "Mentos"
       }
       ```

    - Response Message

       ```json
       {
           "username": "JIN HO",
           "nickname": "Mentos",
           "authorities": [
                   {
                           "authorityName": "ROLE_USER"
                   }
           ]		
       }
       ```


2. 로그인 (POST /sign)
    - Request Message

       ```json
       {
           "username": "JIN HO",
           "password": "12341234"
       }
       ```

    - Response Message

       ```json
      // Response Message 는 RFC6750 표준을 따릅니다. (Body 로 응답), grant_type 은 제외했습니다.
      // https://www.rfc-editor.org/rfc/rfc6750#section-2.1 (4. Example Access Token Response)
       {
           "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
           "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
       }
       ```

3. 리프레시 토큰을 통한 액세스 토큰 재발급 (POST /refresh-token)
    - Request Message

       ```json
      // Refresh Token 재발급 시 요청은 RFC6749 표준을 따릅니다. (Body 로 요청), grant_type 은 제외했습니다.
      // https://www.rfc-editor.org/rfc/rfc6749#section-10.4 (6. Refreshing an Access Token)
       {
           "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
       }
       ```

    - Response Message

       ```json
      // Response Message 는 RFC6750 표준을 따릅니다. (Body 로 응답), token_type 은 제외했습니다.
      // https://www.rfc-editor.org/rfc/rfc6750#section-2.1 (4. Example Access Token Response)
       {
           "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
           "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
       }
       ```

