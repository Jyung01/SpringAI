package com.example.ch05.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class OllamaChatService {

    private final ChatClient chatClient;

    public OllamaChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        당신은 친절한 한국어 음성 대화 도우미입니다.
                        사용자가 듣기 편하도록 핵심부터 자연스럽고 간결하게 답하세요.
                        마크다운 표와 복잡한 기호는 사용하지 마세요.
                        """)
                .build();
    }

    public Flux<String> chat(String question) {
        if (question == null || question.isBlank()) {
            return Flux.just("질문을 입력해 주세요.");
        }

        return chatClient.prompt()
                .user(question.trim())
                .stream()
                .content();
    }
}
