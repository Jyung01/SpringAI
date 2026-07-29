package com.example.ch09.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class JdbcChatService {

    private ChatClient chatClient;

    public JdbcChatService(JdbcChatMemoryRepository jdbcChatMemoryRepository,
                           ChatClient.Builder chatClientBuilder) {

        ChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();

        this.chatClient = chatClientBuilder
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),
                        new SimpleLoggerAdvisor(Ordered.HIGHEST_PRECEDENCE - 1)
                )
                .build();
    }

    public String chat(String question, String conversationId) {
        String answer = chatClient.prompt()
                .user(question)
                .advisors(
                        advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId)
                )
                .call()
                .content();

        return answer;
    }



}
