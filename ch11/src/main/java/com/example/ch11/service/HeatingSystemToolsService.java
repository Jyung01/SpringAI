package com.example.ch11.service;

import com.example.ch11.tool.DateTimeTools;
import com.example.ch11.tool.HeatingSystemTools;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class HeatingSystemToolsService {

    private ChatClient chatClient;
    private HeatingSystemTools heatingSystemTools;

    public HeatingSystemToolsService(ChatClient.Builder chatClientBuilder, HeatingSystemTools heatingSystemTools) {
        this.chatClient = chatClientBuilder.build();
        this.heatingSystemTools = heatingSystemTools;
    }

    public String chat(String question) {

        String answer = this.chatClient
                .prompt()
                .system("""
                        현재 온도가 사용자가 원하는 온도 이상이라면 난방 시스템을 중지하세요
                        현재 온도가 사용자가 원하는 온도 이하라면 난방 시스템을 가동하세요                        
                        """)
                .user(question)
                .tools(heatingSystemTools)
                .toolContext(Map.of("controlKey", "heatingSystemKey"))
                .call()
                .content();
        return answer;
    }

}