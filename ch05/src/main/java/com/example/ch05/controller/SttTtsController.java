package com.example.ch05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SttTtsController {

    @GetMapping("/ai/stt-tts")
    public String sstTts() {
        return "/stt-tts";
    }
}
