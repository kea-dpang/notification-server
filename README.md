# BE_Notification_Server

## 🌐 프로젝트 개요

해당 프로젝트는 DPANG 서비스의 알림 서버로서, 특정 상황이 발생했을 때 사용자에게 메일을 보내는 서비스를 담당하고 있습니다.

## 🛠️ 프로젝트 개발 환경

프로젝트는 아래 환경에서 개발되었습니다.

> OS: Window 10   
> IDE: Intellij IDEA  
> Java 17

## ✅ 프로젝트 실행

해당 프로젝트를 추가로 개발 혹은 실행시켜보고 싶으신 경우 아래의 절차에 따라 진행해주세요

#### 1. `secret.yml` 생성

```commandline
cd ./src/main/resources
touch secret.yml
```

#### 2. `secret.yml` 작성

```text
spring:
  mail:
    host: smtp.naver.com
    port: 465
    username: {Google_Id}
    password: {Google_Password}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```

#### 3. 프로젝트 실행

```commandline
./gradlew bootrun
```

**참고) 프로젝트가 실행 중인 환경에서 아래 URL을 통해 API 명세서를 확인할 수 있습니다**

```commandline
http://localhost:8080/swagger-ui/index.html
```