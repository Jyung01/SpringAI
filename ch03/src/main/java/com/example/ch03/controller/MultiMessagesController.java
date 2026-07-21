package com.example.ch03.controller;

import com.example.ch03.service.MultiMessagesService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MultiMessagesController {

    private static final String HISTORY_KEY = "multiMessagesHistory";

    private final MultiMessagesService service;

    @GetMapping("/ai/multi-messages")
    public String page(HttpSession session) {
        // 페이지를 새로 열면 이전 실습 대화는 초기화한다.
        session.removeAttribute(HISTORY_KEY);
        return "/multi-messages";
    }

    @ResponseBody
    @PostMapping(value = "/ai/multi-messages",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> prompt(@RequestParam String question, HttpSession session) {
        return service.prompt(question, getHistory(session));
    }

    @SuppressWarnings("unchecked")
    private List<Message> getHistory(HttpSession session) {
        Object saved = session.getAttribute(HISTORY_KEY);
        if (saved instanceof List<?>) {
            return (List<Message>) saved;
        }

        List<Message> history = new ArrayList<>();
        session.setAttribute(HISTORY_KEY, history);
        return history;
    }
}
