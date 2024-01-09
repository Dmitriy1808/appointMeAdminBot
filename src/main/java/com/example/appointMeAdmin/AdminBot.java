package com.example.appointMeAdmin;

import com.example.appointMeAdmin.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AdminBot extends TelegramWebhookBot {

    private final BotConfig config;
    private final BotManager botManager;

    public AdminBot(BotConfig config, BotManager botManager) {
        super();
        this.config = config;
        this.botManager = botManager;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return botManager.handleRequest(update);
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
