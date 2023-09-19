package com.example.appointMeAdmin.command.impl;

import com.example.appointMeAdmin.Utils;
import com.example.appointMeAdmin.command.Command;
import com.example.appointMeAdmin.command.CommandProcessor;
import com.example.appointMeAdmin.model.Appointment;
import com.example.appointMeAdmin.model.User;
import com.example.appointMeAdmin.service.AppointmentService;
import com.example.appointMeAdmin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class GetAllAppointmentsCommand implements CommandProcessor {

    private final UserService userService;
    private final AppointmentService appointmentService;

    @Override
    public BotApiMethod<?> processCommand(Update update) {
        User user = userService.getUserByNickname(Utils.getUsernameFromUpdate(update));
        List<Appointment> allAppointments = appointmentService.getAllAppointmentByUser(user);
        long chatId = Utils.getChatId(update);
        if (allAppointments.isEmpty()) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(Utils.NO_APPOINTMENTS_REPLY_MESSAGE_TEMPLATE)
                    .build();
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text(Utils.formatAppointmentsForReplyMessage(allAppointments))
                .build();
    }

    @Override
    public Command getName() {
        return Command.GET_ALL_APPOINTMENTS;
    }
}