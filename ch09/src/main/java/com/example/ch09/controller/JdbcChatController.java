package com.example.ch09.controller;

import com.example.ch09.service.JdbcChatService;
import com.example.ch09.service.VectorStoreChatService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Log4j2
@Controller
public class JdbcChatController {

    private final JdbcChatService service;

    @GetMapping("/ai/jdbc-chat")
    public String jdbcChat() {
        return "/jdbc-chat";
    }

    @ResponseBody
    @PostMapping("/ai/jdbc-chat")
    public String jdbcChat(@RequestParam("question") String question, HttpSession session) {

        String sessionId = session.getId();

        String answer = service.chat(question, sessionId);

        return answer;
    }
}
