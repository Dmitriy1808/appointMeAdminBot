package com.example.appointMeAdmin;

import com.example.appointMeAdmin.command.CommandManager;
import com.example.appointMeAdmin.command.CommandProcessor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private final CommandManager commandManager;

    public BotApiMethod<?> handleRequest(Update update) {
        Message message = update.getMessage();
        String commandText = message == null ? StringUtils.EMPTY : message.getText();
        if (message != null) {
            try {
                CommandProcessor commandProcessor = commandManager.getCommandProcessorFromMessage(message);
                return commandProcessor.processCommand(update);
            } catch (IllegalArgumentException e) {
                log.error("Error while processing command {}: {}", commandText, e.getLocalizedMessage());
                return SendMessage.builder()
                        .chatId(Utils.getChatId(update))
                        .text("Command " + commandText + " was failed, try again later")
                        .build();
            }
        }

        return SendMessage.builder()
                .chatId(Utils.getChatId(update))
                .text("**Неизвестная команда**\nПопробуйте еще раз")
                .build();
    }

}
