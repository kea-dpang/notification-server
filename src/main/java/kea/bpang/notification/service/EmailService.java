package kea.bpang.notification.service;

import kea.bpang.notification.dto.EmailNotificationDto;

public interface EmailService {
    Boolean sendMessage(EmailNotificationDto dto) throws Exception;
}
