package com.example.ch04.dto;

import java.util.List;

/**
 * BeanOutputConverter가 Ollama의 JSON 응답을 변환할 대상 DTO다.
 * record를 사용하면 필드와 생성자 코드를 짧게 표현할 수 있다.
 */
public record HotelInfo(
        String hotelName,
        String city,
        String address,
        Double rating,
        List<String> features
) {
}
