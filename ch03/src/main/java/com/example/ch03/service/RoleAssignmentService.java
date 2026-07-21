package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class RoleAssignmentService {

    private final ChatClient chatClient;

    public RoleAssignmentService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** system 메시지로 모델의 역할과 답변 기준을 먼저 지정한다. */
    public Flux<String> prompt(String requirements) {
        return chatClient.prompt()
                .system("""
                        당신은 현지 사정에 밝은 전문 여행 플래너입니다.
                        사용자의 조건을 빠짐없이 반영해 현실적인 일정을 한국어로 작성하세요.
                        장소별 추천 이유와 이동 순서를 간결하게 설명하세요.
                        """)
                .user(requirements)
                .stream()
                .content();
    }
}
