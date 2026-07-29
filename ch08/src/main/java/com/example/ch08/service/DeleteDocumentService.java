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
        // 메타데이터가 주문취소에 관련된 것들을 삭제한다.
        String filterExpression = "category == '주문취소'";

        vectorStore.delete(filterExpression);
        log.info("벡터 문서 삭제 완료 - 조건: {}", filterExpression);
    }
}
