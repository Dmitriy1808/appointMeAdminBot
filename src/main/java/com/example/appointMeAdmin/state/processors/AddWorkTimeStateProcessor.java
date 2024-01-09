package com.example.appointMeAdmin.state.processors;

import com.example.appointMeAdmin.Utils;
import com.example.appointMeAdmin.service.WorkTimeService;
import com.example.appointMeAdmin.state.MessageProcessor;
import com.example.appointMeAdmin.state.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.ParseException;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddWorkTimeStateProcessor implements MessageProcessor {
    private static final String ADD_WORK_TIME_MESSAGE_TEMPLATE = "ADD_WORK_TIME_MESSAGE_TEMPLATE";
    private static final String ADD_WORK_TIME_ERROR_MESSAGE = "ADD_WORK_TIME_ERROR_MESSAGE";
    private static final String ADD_WORK_TIME_SUCCESSFUL_MESSAGE = "ADD_WORK_TIME_SUCCESSFUL_MESSAGE";

    private final WorkTimeService workTimeService;

    @Override
    public BotApiMethod<?> process(Update update) {
        return SendMessage.builder()
                .chatId(Utils.getChatId(update))
                .text(ADD_WORK_TIME_MESSAGE_TEMPLATE)
                .build();
    }

    @Override
    public State getState() {
        return State.ADD_WORK_TIME;
    }

    @Override
    public BotApiMethod<?> processMessage(Message message) {
        Long chatId = message.getChatId();
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder().chatId(chatId);
        if (!message.hasText()) {
            log.error("Empty work time in message");
            return messageBuilder.text(ADD_WORK_TIME_ERROR_MESSAGE)
                    .build();
        }

        Date preparedDate = getPreparedDate(message.getText());
        log.info("Save work time {} - {}", preparedDate, DateUtils.addHours(preparedDate, 1));
        workTimeService.setWorkTime(preparedDate);
        return messageBuilder.text(ADD_WORK_TIME_SUCCESSFUL_MESSAGE)
                .build();
    }

    private Date getPreparedDate(String rawDate) {
        Date preparedDate = new Date();
        try {
            return DateUtils.parseDate(rawDate, "dd.MM.yyyy HH:mm");
        } catch (ParseException e) {
            log.error("Error while parsing date", e);
        }

        return preparedDate;
    }
}
