package com.example.ch03.controller;

import com.example.ch03.service.AiService;
import com.example.ch03.service.ZeroShotPromptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

@Log4j2
@Controller
@RequiredArgsConstructor
public class ZeroShotPromptController {

    private final ZeroShotPromptService service;

    @GetMapping("/ai/zero-shot-prompt")
    public String promptTemplate() {

        return "/zero-shot-prompt";
    }

    @ResponseBody
    @PostMapping("/ai/zero-shot-prompt")
    public String zeroShotPrompt(String review) {
        String answer = service.prompt(review);

        return answer;
    }

}
