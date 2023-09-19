package com.example.appointMeAdmin.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

@Component
public class CommandManager {

    private final Map<Command, CommandProcessor> commandProcessors;

    public CommandManager(Map<Command, CommandProcessor> commandProcessors) {
        this.commandProcessors = commandProcessors;
    }

    public CommandProcessor getCommandProcessorFromMessage(Message message) throws IllegalArgumentException {
        String commandText = message.getText();
        if (!message.isCommand()) {
            throw new IllegalArgumentException("Unknown command: " + commandText);
        }

        return switch (commandText) {
            case "/getallappointments" -> commandProcessors.get(Command.GET_ALL_APPOINTMENTS);
            default -> throw new IllegalArgumentException("Unknown command: " + commandText);
        };
    }

}
