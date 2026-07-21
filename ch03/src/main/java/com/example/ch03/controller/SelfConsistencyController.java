package com.example.ch03.controller;

import com.example.ch03.service.SelfConsistencyService;
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
public class SelfConsistencyController {

    private final SelfConsistencyService service;

    @GetMapping("/ai/self-consistency")
    public String page() {
        return "/self-consistency";
    }

    @ResponseBody
    @PostMapping(value = "/ai/self-consistency",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> prompt(@RequestParam String content) {
        return service.prompt(content);
    }
}
