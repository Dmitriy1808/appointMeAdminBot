package com.example.appointMeAdmin.state;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StateProcessor {

    BotApiMethod<?> process(Update update);

    State getState();

}
