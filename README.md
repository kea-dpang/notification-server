# BE_Notification_Server

## 🌐 프로젝트 개요

해당 프로젝트는 DPANG 서비스의 알림 서버로서, 특정 상황이 발생했을 때 사용자에게 메일을 보내는 서비스를 담당하고 있습니다.

## 🛠️ 프로젝트 개발 환경

### OS 환경

> macOS Sonoma  
> Windows 10

### 개발 도구

> IDE: Intellij IDEA  
> Java 17

### 빌드 도구

> Gradle

### 주요 플러그인 버전

> Spring Boot: 3.2.1  
> Spring Dependency Management: 1.1.4

## 🔀 프로젝트 아키텍처

아래의 Sequence Diagram은 본 프로젝트의 주요 컴포넌트인 Spring Cloud Gateway, 타 서비스, 그리고 알림 서비스 간의 상호작용을 보여줍니다.

```mermaid
sequenceDiagram
    participant Client as Client
    participant Gateway as Spring Cloud Gateway
    participant OtherService as Other Service
    participant NotificationService as Notification Service
    participant EmailServer as Email Server
    Client ->> Gateway: 요청 전송 (JWT 토큰 포함)
    Gateway ->> Gateway: 요청 인증 및 인가

    alt 인증 성공
        Gateway ->> OtherService: 요청 전달 (X-DPANG-CLIENT-ID 헤더 추가)
        OtherService ->> NotificationService: API 요청
        NotificationService ->> NotificationService: 해당 요청 권한 식별

        alt 요청이 역할에 적합
            NotificationService ->> EmailServer: 이메일 전송 요청
            EmailServer -->> NotificationService: 이메일 전송 응답
            NotificationService ->> NotificationService: 응답 처리
            NotificationService -->> OtherService: 응답 전송
            OtherService -->> Gateway: 응답 전송
            Gateway -->> Client: 최종 응답 전달

        else 요청이 역할에 부적합
            NotificationService -->> OtherService: 사용자 권한 없음 응답
            OtherService -->> Gateway: 사용자 권한 없음 응답
            Gateway -->> Client: 사용자 권한 없음 응답
        end

    else 인증 실패
        Gateway -->> Client: 인증 실패 응답
    end

```

시퀀스 다이어그램을 통해 확인할 수 있듯이, 클라이언트로부터의 요청은 초기 단계에서 Spring Cloud Gateway를 통과하게 됩니다. 이 과정에서 사용자 인증이 이루어지며, 이 인증이 성공적으로 완료되어야만
서비스 요청이 이어집니다.

인증 과정이 정상적으로 마무리되면, 'X-DPANG-CLIENT-ID'라는 사용자 정의 헤더에 사용자의 ID 정보가 포함되어 전달됩니다. 이 헤더는 알림 서비스로의 요청에 함께 첨부되어, 알림 서비스가 요청을 한
사용자를 정확하게 파악할 수 있게 도와줍니다. 이렇게 사용자 식별에 성공한 요청은 이메일 서버로 전송되어 알림을 발송하고, 그 결과에 따라 최종 응답이 반환됩니다.

## 📑 API 명세

알림 서비스는 다음과 같은 API를 제공합니다:

1. 이메일 알림 전송: 특정 상황이 발생했을 때 정보를 전달하고자 할 때 사용합니다. 이메일을 통해 알림을 전송합니다.

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
    username: { Google_Id }
    password: { Google_Password }
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
```

#### 3. 프로젝트 실행

```commandline
./gradlew bootrun
```

**참고) 프로젝트가 실행 중인 환경에서 아래 URL을 통해 API 명세서를 확인할 수 있습니다**

```commandline
http://localhost:8080/swagger-ui/index.html
```