package com.example.ch04.dto;

import java.util.List;

/** system message 예제의 구조화된 리뷰 분석 결과다. */
public record ReviewAnalysis(
        String sentiment,
        String summary,
        List<String> keywords
) {
}
