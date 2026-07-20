package com.example.ch01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class AiController {

    @PostMapping(
            value = "/ai/chat",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, // 클라이언트가 보내는 데이터 타입, 기본 폼 전송, 일반적으로 생략
            produces = MediaType.TEXT_PLAIN_VALUE                   // 서버가 응답하는 데이터타입, 일반 텍스트 일반적으로 생략
    )
    public String chat(String question) {

        // 시스템 메시지 작성
        SystemMessage systemMessage = SystemMessage.builder()
                .text("사용자 질문에 한국어로 답변해야 됩니다.")
                .build();

        // 사용자 메시지 작성
        UserMessage userMessage = UserMessage.builder()
                .text(question)
                .build();

        // 대화 옵션 설정
        ChatOptions chatOptions = ChatOptions.builder()

                .build();

        // 프롬프트 생성

        // LLM 요청 및 응답

        return "아직 모델과 연결되지 않았습니다.";

    }

}
