package com.example.ch04.service;

import com.example.ch04.dto.ReviewAnalysis;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SystemMessageService {

    private final ChatClient chatClient;

    public SystemMessageService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** system 메시지로 역할과 허용되는 감정 레이블을 고정한다. */
    public ReviewAnalysis analyze(String review) {
        return chatClient.prompt()
                .system("""
                        당신은 영화 리뷰 분석가입니다.
                        sentiment는 반드시 긍정, 중립, 부정 중 하나로 작성하세요.
                        summary는 한국어 한 문장으로, keywords는 핵심 단어 세 개 이하로 작성하세요.
                        """)
                .user(review)
                .call()
                .entity(ReviewAnalysis.class);
    }
}
