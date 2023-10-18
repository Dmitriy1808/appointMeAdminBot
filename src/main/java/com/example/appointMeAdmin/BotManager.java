package com.example.appointMeAdmin;

import com.example.appointMeAdmin.state.State;
import com.example.appointMeAdmin.state.StateManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
@Component
@Getter
@Setter
@Slf4j
public class BotManager {
    private static final String INITIAL_STATE_MESSAGE = "INITIAL_STATE_MESSAGE";
    private static final String UNKNOWN_COMMAND_REPLY_TEXT = "Неизвестная команда\nПопробуйте еще раз";

    private State currentState = State.INITIAL_STATE;

    private final StateManager stateManager;

    public BotApiMethod<?> handleRequest(Update update) {
        Message message = update.getMessage();
        if (message != null && message.isCommand() && message.hasText()) {
            setCurrentState(getStateByCommand(message.getText()));
        }

        if (update.hasCallbackQuery()) {
            return stateManager.getCallbackProcessor(currentState).processCallback(update.getCallbackQuery());
        }

        if (message != null && !message.isCommand() && message.hasText()) {
            return stateManager.getMessageProcessor(currentState).processMessage(message);
        }

        if (message != null && message.isCommand()) {
            return stateManager.getStateProcessor(currentState).process(update);
        }

        setCurrentState(State.INITIAL_STATE);
        return SendMessage.builder()
                .chatId(Utils.getChatId(update))
                .text(UNKNOWN_COMMAND_REPLY_TEXT)
                .build();
    }

    private State getStateByCommand(String commandText) throws IllegalStateException {
        return switch (commandText) {
            case "/addworktime" -> State.ADD_WORK_TIME;
            case "/declineappointment" -> State.DECLINE_APPOINTMENT;
            case "/appointments" -> State.GET_ALL_APPOINTMENTS;
            case "/start" -> State.INITIAL_STATE;
            default -> throw new IllegalStateException();
        };
    }

}
