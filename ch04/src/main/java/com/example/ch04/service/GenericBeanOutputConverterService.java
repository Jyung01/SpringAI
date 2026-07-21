package com.example.ch04.service;

import com.example.ch04.dto.CityHotels;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericBeanOutputConverterService {

    private final ChatClient chatClient;

    public GenericBeanOutputConverterService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * List<CityHotels>처럼 제네릭 정보가 있는 타입은 Class만으로 표현할 수 없으므로
     * ParameterizedTypeReference를 사용해 전체 타입 정보를 전달한다.
     */
    public List<CityHotels> convert(String cities) {
        return chatClient.prompt()
                .user("""
                        다음 각 도시마다 대표 호텔 두 곳을 정리하세요: %s
                        도시별 결과를 빠짐없이 반환하세요.
                        """.formatted(cities))
                .call()
                .entity(new ParameterizedTypeReference<List<CityHotels>>() { });
    }
}
