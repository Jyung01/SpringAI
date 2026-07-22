package com.example.ch06.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;

@Service
public class ImageAnalysisService {

    private final ChatClient chatClient;

    public ImageAnalysisService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public Flux<String> analysis(
            String question,
            String contentType,
            byte[] imageBytes) {

        SystemMessage systemMessage = SystemMessage.builder()
                .text("""
                        당신은 이미지 분석 전문가입니다.
                        이미지에서 실제로 확인되는 내용만 한국어로 설명하세요.
                        확실하지 않은 내용은 추측이라고 명시하세요.
                        """)
                .build();

        // 업로드한 이미지 바이트를 Ollama가 받을 Media 객체로 만든다.
        Media media = Media.builder()
                .mimeType(MimeType.valueOf(contentType))
                .data(new ByteArrayResource(imageBytes))
                .build();

        UserMessage userMessage = UserMessage.builder()
                .text(question)
                .media(media)
                .build();

        Prompt prompt = Prompt.builder()
                .messages(systemMessage, userMessage)
                .build();

        // 비전 모델의 분석 결과를 조각 단위로 브라우저에 전달한다.
        return chatClient.prompt(prompt).stream().content();
    }
}
