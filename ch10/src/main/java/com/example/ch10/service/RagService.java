package com.example.ch10.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentReader;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class RagService {

    private ChatClient chatClient;
    private VectorStore vectorStore;
    private JdbcTemplate jdbcTemplate;

    public RagService(ChatClient.Builder chatClientBuilder,
                      VectorStore vectorStore,
                      JdbcTemplate jdbcTemplate) {

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(Ordered.HIGHEST_PRECEDENCE - 1)
                )
                .build();
        this.vectorStore = vectorStore;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clearVectorStore() {
        jdbcTemplate.update("TRUNCATE TABLE vector_store");
    }

    public void ragEtl(MultipartFile attach, String source, int chunkSize, int minChunkSizeChars) throws IOException {

        // 추출
        Resource resource = new ByteArrayResource(attach.getBytes());
        DocumentReader reader = new PagePdfDocumentReader(resource);
        List<Document> documentList = reader.read();

        // 메타데이터 추가
        for (Document document : documentList) {
            document.getMetadata().put("source", source);
        }

        // 변환
        DocumentTransformer transformer = TokenTextSplitter
                .builder()
                .withChunkSize(chunkSize)
                .withMinChunkSizeChars(minChunkSizeChars)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .build();

        List<Document> transformedDocumentList = transformer.apply(documentList);

        // 적재
        vectorStore.add(transformedDocumentList);

    }

    public String ragChat(String question, Double score, String source) {

        // 벡터 저장소 검색 조건 생성
         SearchRequest.Builder searchRequestBuilder = SearchRequest
                .builder()
                .similarityThreshold(score)
                .topK(3);

         if (StringUtils.hasText(source)) {
            searchRequestBuilder.filterExpression("source == '%s'".formatted(source));
         }

         SearchRequest searchRequest = searchRequestBuilder.build();

         // 검색 증강 어드바이저 생성
        QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor
                .builder(vectorStore)
                .searchRequest(searchRequest)
                .build();

        // LLM 요청 및 응답
        String answer = chatClient
                .prompt()
                .user(question)
                .advisors(questionAnswerAdvisor)
                .call()
                .content();

        return answer;
    }


}
