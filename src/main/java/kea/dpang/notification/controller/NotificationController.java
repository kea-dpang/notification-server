package kea.dpang.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.notification.base.BaseResponse;
import kea.dpang.notification.dto.EmailNotificationDto;
import kea.dpang.notification.dto.SlackNotificationDto;
import kea.dpang.notification.service.EmailService;
import kea.dpang.notification.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final SlackService slackService;

    @Operation(summary = "이메일 전송", description = "특정 상황이 발생했을 때 이메일로 정보를 전달하고자 할 때 사용합니다.")
    @PostMapping("/email")
    public ResponseEntity<BaseResponse> sendEmailNotification(
            @RequestBody EmailNotificationDto dto
    ) {
        emailService.sendMessage(dto);
        log.info("이메일 알림이 성공적으로 보내졌습니다.");

        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), "이메일 알림이 성공적으로 보내졌습니다."));
    }

    @Operation(summary = "Slack 메시지 전송", description = "특정 상황이 발생했을 때 슬랙으로 정보를 전달하고자 할 때 사용합니다.")
    @PostMapping("/slack")
    public ResponseEntity<BaseResponse> sendSlackNotification(
            @RequestBody SlackNotificationDto dto
    ) {
        slackService.sendMessage(dto.getMessage());
        log.info("Slack 알림이 성공적으로 보내졌습니다.");

        return ResponseEntity.ok(new BaseResponse(HttpStatus.OK.value(), "Slack 알림이 성공적으로 보내졌습니다."));
    }
}