package com.example.ch08.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class DeleteDocumentService {

    private final VectorStore vectorStore;

    public void deleteDocument() {
        // 메타데이터가 헌법이고 연도가 1987 이상인 문서만 삭제한다.
        String filterExpression = "source == '헌법' && year >= 1987";

        vectorStore.delete(filterExpression);
        log.info("벡터 문서 삭제 완료 - 조건: {}", filterExpression);
    }
}
