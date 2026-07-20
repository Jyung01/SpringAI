package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FewShotPromptService {

    private final ChatClient chatClient;

    public FewShotPromptService(ChatClient.Builder chatClientBuilder) {
        chatClient = chatClientBuilder.build();
    }

    public String prompt(String order) {

        String prompt = """
                주문을 JSON 형식으로 변환하시오.
                추가 설명은 하지마세요.
                
                예시1:
                작은 피자 하나, 치즈랑 토마토 소스, 페페로니로 주세요.
                
                JSON 응답:
                {
                    "size": "small",
                    "type": "normal",
                    "ingredients": ["cheese", "tomato source", "pepperoni"],
                }
                
                고객주문 : %s""".formatted(order);

        Prompt prompt1 =

        String answer = chatClient.prompt()

                .call()
                .content();

        return answer;
    }



}
