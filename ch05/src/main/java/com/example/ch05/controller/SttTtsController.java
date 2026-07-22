package com.example.ch05.controller;

import com.example.ch05.service.OllamaChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor
public class SttTtsController {

    private final OllamaChatService ollamaChatService;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/ai/stt-tts")
    public String sttTts() {
        return "/stt-tts";
    }

    @GetMapping({
            "/ai/stt-llm-tts",
            "/ai/chat-voice-stt-llm-tts",
            "/ai/chat-voice-one-model"
    })
    public String voiceChat() {
        return "/voice-chat";
    }

    @ResponseBody
    @PostMapping(
            value = "/ai/chat-text",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> chatText(@RequestParam String question) {
        return ollamaChatService.chat(question);
    }
}
