package com.example.ch11.service;

import java.util.Map;

import com.example.ch11.tool.RecommendMovieTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RecommendMovieToolsService {

    private ChatClient chatClient;
    private RecommendMovieTools recommendMovieTools;

    public RecommendMovieToolsService(ChatClient.Builder chatClientBuilder, RecommendMovieTools recommendMovieTools) {
        this.chatClient = chatClientBuilder.build();
        this.recommendMovieTools = recommendMovieTools;
    }

    public String chat(String question) {

        String answer = this.chatClient
                .prompt()
                .user(question)
                .tools(recommendMovieTools)
                .call()
                .content();
        return answer;
    }

}