package kea.bpang.notification.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.bpang.notification.dto.EmailNotificationDto;
import kea.bpang.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;

    @Operation(summary = "이메일 전송", description = "특정 상황이 발생했을 때 정보를 전달하고자 할 때 사용합니다.")
    @PostMapping("/email")
    public ResponseEntity<String> sendEmailNotification(@RequestBody EmailNotificationDto dto) {
        try {
            Boolean isSent = emailService.sendMessage(dto);
            if (isSent) {
                log.info("이메일 알림이 성공적으로 보내졌습니다.");
                return ResponseEntity.ok("이메일 알림이 성공적으로 보내졌습니다.");

            } else {
                log.error("이메일 알림을 보내는 데 실패하였습니다.");
                return ResponseEntity.status(500).body("이메일 알림을 보내는 데 실패하였습니다.");
            }

        } catch (Exception e) {
            log.error("이메일 알림을 보내는 중 오류가 발생했습니다: ", e);
            return ResponseEntity.status(500).body("이메일 알림을 보내는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
