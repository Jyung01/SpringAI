package com.example.ch04.dto;

import java.util.List;

/** 도시 하나와 그 도시의 추천 호텔 목록을 묶는 중첩 DTO다. */
public record CityHotels(
        String city,
        List<HotelInfo> hotels
) {
}
