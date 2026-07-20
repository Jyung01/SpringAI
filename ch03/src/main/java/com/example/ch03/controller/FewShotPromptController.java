package com.example.ch03.controller;

import com.example.ch03.service.ZeroShotPromptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@Controller
@RequiredArgsConstructor
public class FewShotPromptController {

    private final ZeroShotPromptService service;

    @GetMapping("/ai/few-shot-prompt")
    public String promptTemplate() {

        return "/few-shot-prompt";
    }

    @ResponseBody
    @PostMapping("/ai/few-shot-prompt")
    public String zeroShotPrompt(String review) {
        String answer = service.prompt(review);

        return answer;
    }

}
