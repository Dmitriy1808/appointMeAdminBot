package com.example.appointMeAdmin.controller;

import com.example.appointMeAdmin.AdminBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

    private final AdminBot bot;

    public WebHookController(AdminBot bot) {
        this.bot = bot;
    }

    @PostMapping("/admin")
    public BotApiMethod<?> onReceive(@RequestBody Update update) {
        return bot.onWebhookUpdateReceived(update);
    }

}
