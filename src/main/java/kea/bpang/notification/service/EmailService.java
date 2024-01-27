package kea.bpang.notification.service;

import kea.bpang.notification.dto.EmailNotificationDto;

public interface EmailService {

    /**
     * 알림 전송 (이메일 전송)
     *
     * @param dto 어떤 내용을 누구에게 보낼지에 관한 dto
     */
    void sendMessage(EmailNotificationDto dto);
}
