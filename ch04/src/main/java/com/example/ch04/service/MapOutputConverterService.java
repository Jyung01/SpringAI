package com.example.ch04.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MapOutputConverterService {

    private final ChatClient chatClient;

    public MapOutputConverterService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /** 필드가 고정된 DTO가 필요 없을 때 MapOutputConverter를 사용할 수 있다. */
    public Map<String, Object> convert(String hotel) {
        return chatClient.prompt()
                .user("""
                        %s의 정보를 hotelName, city, address, grade, features 키로 정리하세요.
                        확인할 수 없는 값은 지어내지 말고 null 또는 빈 목록으로 작성하세요.
                        """.formatted(hotel))
                .call()
                .entity(new MapOutputConverter());
    }
}
