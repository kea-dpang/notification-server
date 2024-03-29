# DPANG Notification Server

## 🌐 프로젝트 개요

해당 프로젝트는 DPANG 서비스의 알림 서버로서, 특정 상황이 발생했을 때 이메일 및 슬랙으로 알림을 전송하는 역할을 수행합니다.

이를 통해 사용자의 경험을 향상시키고, 사용자에게 필요한 정보를 적시에 전달함으로써 서비스의 가치를 높이는데 중점을 두고 있습니다.

## 🔀 프로젝트 아키텍처

```mermaid
sequenceDiagram
    participant Client as Client
    participant Gateway as Spring Cloud Gateway
    participant OtherService as Other Service
    participant Notification as Notification Service
    
    Client ->> Gateway: 요청 전송
    Gateway ->> Gateway: 요청에 대한 인증 및 권한 확인

    opt 인증 및 인가 성공
        Gateway ->> OtherService: 클라이언트 요청 전달 <br> (X-DPANG-CLIENT-ID, X-DPANG-CLIENT-ROLE 헤더 추가)
        OtherService ->> OtherService: 클라이언트 요청 처리
        OtherService ->> Notification: 알림 전송 요청 <br> (X-DPANG-SERVICE-NAME 헤더 추가)
        Notification ->> Notification: 알림 전송
        Notification ->> OtherService: 알림 전송 결과 전달
        OtherService ->> OtherService: 클라이언트 요청에 대한 최종 처리
        OtherService ->> Gateway: 처리 결과 전달
        Gateway ->> Client: 최종 처리 결과 응답
    end

    opt 인증 실패
        Gateway -->> Client: 인증 실패 응답
    end

```

## ✅ 프로젝트 실행

해당 프로젝트를 추가로 개발 혹은 실행시켜보고 싶으신 경우 아래의 절차에 따라 진행해주세요

#### 1. `secret.yml` 생성

```commandline
cd ./src/main/resources
touch secret.yml
```

#### 2. `secret.yml` 작성

```yaml
spring:
  mail:
    host: smtp.naver.com
    port: 465
    username: { YOUR_GOOGLE_ID }
    password: { YOUR_GOOGLE_PASSWORD }
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true

  application:
    name: notification-server

eureka:
  instance:
    prefer-ip-address: true

  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://{YOUR_EUREKA_SERVER_IP}:{YOUR_EUREKA_SERVER_PORT}/eureka/

slack:
  webhookUrl: { YOUR_SLACK_WEBHOOK_URL }
```

#### 3. 프로젝트 실행

```commandline
./gradlew bootrun
```

**참고) 프로젝트가 실행 중인 환경에서 아래 URL을 통해 API 명세서를 확인할 수 있습니다**

```commandline
http://localhost:8080/swagger-ui/index.html
```