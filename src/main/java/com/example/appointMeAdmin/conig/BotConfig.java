package com.example.appointMeAdmin.conig;

import com.example.appointMeAdmin.command.Command;
import com.example.appointMeAdmin.command.CommandProcessor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Getter
public class BotConfig {

    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public Map<Command, CommandProcessor> commandProcessors(List<CommandProcessor> processors) {
        return processors.stream().collect(Collectors.toMap(CommandProcessor::getName, Function.identity()));
    }

}