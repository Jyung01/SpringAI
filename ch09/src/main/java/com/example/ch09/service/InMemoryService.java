package com.example.ch09.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class InMemoryService {

    private ChatClient chatClient;

    public InMemoryService(ChatMemory chatMemory, ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor(Ordered.HIGHEST_PRECEDENCE - 1)
                )
                .build();
    }

    public String chat(String question, String conversationId) {
        String answer = chatClient.prompt()
                .user(question)
                .advisors(
                        advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId)
                )
                .call()
                .content();

        return answer;
    }



}
