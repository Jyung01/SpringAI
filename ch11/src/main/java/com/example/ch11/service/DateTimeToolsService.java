package com.example.ch11.service;

import com.example.ch11.tool.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;


import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class DateTimeToolsService {

    private ChatClient chatClient;
    private DateTimeTools dateTimeTools;

    public DateTimeToolsService(ChatClient.Builder chatClientBuilder, DateTimeTools dateTimeTools) {
        this.chatClient = chatClientBuilder.build();
        this.dateTimeTools = dateTimeTools;
    }

    public String chat(String question) {

        String answer = this.chatClient
                .prompt()
                .user(question)
                .tools(dateTimeTools)
                .call()
                .content();
        return answer;
    }

}