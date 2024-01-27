package kea.bpang.notification.service;

import kea.bpang.notification.dto.EmailNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String myEmail;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public Boolean sendMessage(EmailNotificationDto dto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(myEmail); // 누가 보냈는지 설정
            message.setTo(dto.getTo()); // 누구에게 보낼건지 설정
            message.setSubject(dto.getTitle()); // 이메일 제목
            message.setText(dto.getBody()); // 이메일 본문
            emailSender.send(message);

            log.info("이메일을 성공적으로 보냈습니다.");
            return true;

        } catch (Exception e) {
            log.error("이메일을 보내는 도중 오류가 발생했습니다: ", e);
            return false;
        }
    }
}