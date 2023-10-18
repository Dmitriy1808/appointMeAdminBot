package com.example.appointMeAdmin.state.processors.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
@Slf4j
public class MessageSender {
    private String botOwnerNotifyUrl = """
            https://api.telegram.org/bot%s/sendMessage""";
    @Value("${telegram.bot.token:}")
    private String botToken;
    @Value("${bot.owner.id:}")
    private long botOwnerId;

    private final RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        botOwnerNotifyUrl = botOwnerNotifyUrl.formatted(botToken);
    }

    public void sendMessage(String message) {
        ResponseEntity<String> response = restTemplate.exchange(botOwnerNotifyUrl,
                HttpMethod.POST,
                new HttpEntity<>(new UserParams(botOwnerId, message)),
                String.class);

        if (!response.hasBody()) {
            log.error("Notification sending failed. Empty response from Telegram");
        } else if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error("Notification sending failed. Response from Telegram: {}", response.getStatusCode());
        }
    }
}
