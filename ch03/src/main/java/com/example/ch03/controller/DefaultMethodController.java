package com.example.ch03.controller;

import com.example.ch03.service.DefaultMethodService;
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
public class DefaultMethodController {

    private final DefaultMethodService service;

    @GetMapping("/ai/default-method")
    public String page() {
        return "/default-method";
    }

    @ResponseBody
    @PostMapping(value = "/ai/default-method",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> prompt(@RequestParam String question) {
        return service.prompt(question);
    }
}
