package com.example.ch10.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;


import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TranslationQueryTransformerService {

    private ChatClient chatClient;
    private ChatModel chatModel;
    private ChatMemory chatMemory;
    private VectorStore vectorStore;

    public TranslationQueryTransformerService(ChatClient.Builder chatClientBuilder,
                                              ChatModel chatModel,
                                              ChatMemory chatMemory,
                                              VectorStore vectorStore) {

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        SimpleLoggerAdvisor
                                .builder()
                                .order(Ordered.LOWEST_PRECEDENCE - 1)
                                .build()
                )
                .build();

        this.chatModel = chatModel;
        this.chatMemory = chatMemory;
        this.vectorStore = vectorStore;
    }

    public TranslationQueryTransformer createTranslationQueryTransformer() {

        ChatClient.Builder chatClientBuilder = ChatClient
                .builder(chatModel)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
                );

        // 질문 번역기 생성
        TranslationQueryTransformer translationQueryTransformer = TranslationQueryTransformer
                .builder()
                .chatClientBuilder(chatClientBuilder)
                .targetLanguage("korean")
                .build();

        return translationQueryTransformer;
    }


    public VectorStoreDocumentRetriever createVectorStoreDocumentRetriever(double score, String source) {

        VectorStoreDocumentRetriever vectorStoreDocumentRetriever = VectorStoreDocumentRetriever
                .builder()
                .vectorStore(vectorStore)
                .similarityThreshold(score)
                .topK(10)
                .build();

        return vectorStoreDocumentRetriever;

    }

    public String chatWithTranslationQuery(String question, double score, String source, String conversationId) {

        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(createTranslationQueryTransformer())
                .documentRetriever(createVectorStoreDocumentRetriever(score, source))
                .build();

        // 프롬프트를 LLM으로 전송하고 응답을 받는 코드
        String answer = this.chatClient.prompt()
                .user(question)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        retrievalAugmentationAdvisor
                )
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return answer;
    }
}
