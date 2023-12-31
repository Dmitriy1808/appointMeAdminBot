package com.example.appointMeAdmin.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallbackProcessor extends StateProcessor {

    BotApiMethod<?> processCallback(CallbackQuery callbackQuery);

}
