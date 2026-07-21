package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class FewShotPromptService {

    private final ChatClient chatClient;

    public FewShotPromptService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.build();
    }

    public String prompt(String order) {

        // 예시 입출력을 프롬프트에 포함하면 모델이 같은 형식을 따라 답한다.
        String prompt = """
                주문을 JSON 형식으로 변환하시오.
                JSON 이외의 설명이나 마크다운 코드 블록은 출력하지 마세요.
                
                예시1:
                작은 피자 하나, 치즈랑 토마토 소스, 페페로니로 주세요.
                
                JSON 응답:
                {
                    "size": "small",
                    "type": "normal",
                    "ingredients": ["cheese", "tomato sauce", "pepperoni"]
                }
                
                고객주문 : %s""".formatted(order);

        // 위의 예시(few-shot)와 실제 고객 주문을 하나의 사용자 메시지로 전달한다.
        String answer = chatClient.prompt()
                .user(prompt)
                .call()
                .content();

        return answer;
    }



}
