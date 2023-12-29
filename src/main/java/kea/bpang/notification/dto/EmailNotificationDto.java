package kea.bpang.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EmailNotificationDto {

    @Schema(example = "dpang@dpang.com", requiredProperties = "true", description = "유저 이메일")
    private String to; // 이메일 보내는 대상

    @Schema(example = "[DPANG] 새로운 사용자가 회원가입을 요청하였습니다.", requiredProperties = "true", description = "이메일 제목")
    private String title; // 이메일 제목

    @Schema(example = "새로운 사용자가 회원가입을 요청하였습니다. 확인 후 필요한 조치를 취해주시기 바랍니다.\n", requiredProperties = "true", description = "이메일 내용")
    private String body; // 이메일 내용
}
