package com.example.ch09.controller;

import com.example.ch09.service.InMemoryService;
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
public class InMemoryController {

    private final InMemoryService service;

    @GetMapping("/ai/in-memory-chat")
    public String inMemoryChat() {
        return "/in-memory-chat";
    }

    @ResponseBody
    @PostMapping("/ai/in-memory-chat")
    public String inMemoryChat(@RequestParam("question") String question, HttpSession session) {

        String sessionId = session.getId();

        String answer = service.chat(question, sessionId);

        return answer;
    }
}
