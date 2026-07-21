package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class SelfConsistencyService {

    private final ChatClient chatClient;

    public SelfConsistencyService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** 같은 내용을 세 번 독립적으로 분석하고, 공통된 판단을 최종 답으로 선택한다. */
    public Flux<String> prompt(String content) {
        List<String> candidates = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            String candidate = chatClient.prompt()
                    .user("""
                            다음 메일이 스팸인지 정상 메일인지 판단하고 핵심 근거를 간단히 설명하세요.
                            다른 분석 결과를 가정하지 말고 독립적으로 판단하세요.

                            메일: %s
                            """.formatted(content))
                    .call()
                    .content();
            candidates.add(candidate);
        }

        // 여러 후보 중 가장 일관된 결론을 모델이 종합하도록 한 번 더 요청한다.
        return chatClient.prompt()
                .user("""
                        아래 세 분석을 비교하여 다수의 결론과 공통 근거를 반영한 최종 답을 작성하세요.
                        첫 줄에는 '스팸' 또는 '정상 메일' 중 하나만 명확히 표시하세요.

                        %s
                        """.formatted(String.join("\n\n---\n\n", candidates)))
                .stream()
                .content();
    }
}
