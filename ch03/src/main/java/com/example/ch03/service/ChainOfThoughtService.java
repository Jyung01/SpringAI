package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChainOfThoughtService {

    private final ChatClient chatClient;

    public ChainOfThoughtService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** 복잡한 문제를 작은 단계로 나누고, 확인 가능한 계산 근거와 답을 요청한다. */
    public Flux<String> prompt(String question) {
        String prompt = """
                다음 문제를 작은 단계로 나누어 해결하세요.
                각 단계에는 사용한 사실이나 계산식을 간단히 적고 마지막에 최종 답을 명확히 표시하세요.

                문제: %s
                """.formatted(question);

        return chatClient.prompt().user(prompt).stream().content();
    }
}
