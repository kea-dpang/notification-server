# DPANG Notification Server

## 🌐 프로젝트 개요

해당 프로젝트는 DPANG 서비스의 알림 서버로서, 특정 상황이 발생했을 때 이메일 및 슬랙으로 알림을 전송하는 역할을 수행합니다.

이를 통해 사용자의 경험을 향상시키고, 사용자에게 필요한 정보를 적시에 전달함으로써 서비스의 가치를 높이는데 중점을 두고 있습니다.

## 🔀 프로젝트 아키텍처

아래의 시퀀스 다이어그램은 본 프로젝트의 주요 컴포넌트인 Spring Cloud Gateway, 타 서비스, 그리고 알림 서비스 간의 상호작용을 보여줍니다.

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

이 시퀀스 다이어그램을 통해 볼 수 있듯이, 모든 클라이언트 요청은 먼저 Spring Cloud Gateway를 통해 전달됩니다.

Gateway는 클라이언트의 요청에 대한 토큰을 분석하고, 사용자의 ID와 Role 정보를 추출하여
'X-DPANG-CLIENT-ID'와 'X-DPANG-CLIENT-ROLE'이라는 사용자 정의 헤더에 추가하여 타 서비스에 전달합니다.

타 서비스에서 사용자에게 알림을 보내야 하는 경우, 해당 서비스는 'X-DPANG-SERVICE-NAME' 헤더에 자신의 서비스 이름을 추가하여 알림 서비스에 알림 요청을 전달합니다.

이후 알림 서비스는 알림 요청을 처리하고, 그 처리 결과를 다른 서비스를 통해 클라이언트에게 반환합니다.

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