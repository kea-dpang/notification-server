package kea.dpang.notification.service;

import com.slack.api.Slack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SlackServiceImpl implements SlackService {

    @Value("${slack.webhookUrl}")
    private String webhookUrl;

    @Override
    public void sendMessage(String message) {
        try {
            Slack.getInstance().send(webhookUrl, "{\"text\":\"" + message + "\"}");
        } catch (Exception err) {
            log.info(err.getMessage());
        }
    }

}
