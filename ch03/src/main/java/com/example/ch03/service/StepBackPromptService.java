package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class StepBackPromptService {

    private final ChatClient chatClient;

    public StepBackPromptService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** 먼저 일반 원칙을 구한 뒤, 그 원칙을 원래 질문에 적용하는 step-back 예제다. */
    public Flux<String> prompt(String question) {
        String principles = chatClient.prompt()
                .user("""
                        아래 질문에 답하기 전에 고려해야 할 일반적인 원칙과 판단 기준을 정리하세요.
                        아직 구체적인 결론은 내리지 마세요.

                        질문: %s
                        """.formatted(question))
                .call()
                .content();

        return chatClient.prompt()
                .user("""
                        다음 일반 원칙을 참고하여 원래 질문에 구체적으로 답하세요.

                        [일반 원칙]
                        %s

                        [원래 질문]
                        %s
                        """.formatted(principles, question))
                .stream()
                .content();
    }
}
