package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DefaultMethodService {

    private final ChatClient chatClient;

    public DefaultMethodService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** 별도의 프롬프트 기법 없이 사용자의 질문을 그대로 모델에 전달한다. */
    public Flux<String> prompt(String question) {
        return chatClient.prompt()
                .user(question)
                .stream()
                .content();
    }
}
