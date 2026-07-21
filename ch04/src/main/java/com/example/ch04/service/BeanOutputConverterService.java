package com.example.ch04.service;

import com.example.ch04.dto.HotelInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Service;

@Service
public class BeanOutputConverterService {

    private final ChatClient chatClient;

    public BeanOutputConverterService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * entity(HotelInfo.class)는 DTO 구조를 설명하는 포맷을 프롬프트에 추가하고,
     * Ollama가 반환한 JSON을 HotelInfo 객체로 역직렬화한다.
     */
    public HotelInfo convert(String city) {
        return chatClient.prompt()
                .user("""
                        %s에서 유명한 호텔 한 곳의 정보를 알려주세요.
                        확인할 수 없는 값은 지어내지 말고 null 또는 빈 목록으로 작성하세요.
                        """.formatted(city))
                .call()
                .entity(HotelInfo.class);
    }

    /** 변환기가 내부적으로 하는 일을 확인할 수 있도록 남겨 둔 저수준 예제다. */
    public HotelInfo convertLowLevel(String city) {
        BeanOutputConverter<HotelInfo> converter = new BeanOutputConverter<>(HotelInfo.class);
        String answer = chatClient.prompt()
                .user("""
                        %s에서 유명한 호텔 한 곳의 정보를 알려주세요.
                        %s
                        """.formatted(city, converter.getFormat()))
                .call()
                .content();
        return converter.convert(answer);
    }
}
