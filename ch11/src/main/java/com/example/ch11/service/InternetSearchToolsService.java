package com.example.ch11.service;

import java.util.Map;

import com.example.ch11.tool.InternetSearchTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.content.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class InternetSearchToolsService {

    private ChatClient chatClient;

    @Autowired
    private InternetSearchTools internetSearchTools;

    public InternetSearchToolsService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String chat(String question) {

        // LLM으로 요청하고 응답받기
        String answer = chatClient
                .prompt()
                .user(question)
                .tools(internetSearchTools)
                .call()
                .content();
        return answer;
    }

}
