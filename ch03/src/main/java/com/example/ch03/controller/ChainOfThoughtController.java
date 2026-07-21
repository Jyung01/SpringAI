package com.example.ch03.controller;

import com.example.ch03.service.ChainOfThoughtService;
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
public class ChainOfThoughtController {

    private final ChainOfThoughtService service;

    @GetMapping("/ai/chain-of-thought")
    public String page() {
        return "/chain-of-thought";
    }

    @ResponseBody
    @PostMapping(value = "/ai/chain-of-thought",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> prompt(@RequestParam String question) {
        return service.prompt(question);
    }
}
