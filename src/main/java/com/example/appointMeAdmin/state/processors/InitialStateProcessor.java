package com.example.appointMeAdmin.state.processors;

import com.example.appointMeAdmin.Utils;
import com.example.appointMeAdmin.state.State;
import com.example.appointMeAdmin.state.StateProcessor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InitialStateProcessor implements StateProcessor {

    private static final String INITIAL_STATE_MESSAGE = "INITIAL_STATE_MESSAGE\n*Жирный текст*";

    @Override
    public BotApiMethod<?> process(Update update) {
        return SendMessage.builder()
                .chatId(Utils.getChatId(update))
                .text(INITIAL_STATE_MESSAGE)
//                .parseMode("MarkdownV2")
                .build();
    }

    @Override
    public State getState() {
        return State.INITIAL_STATE;
    }
}
