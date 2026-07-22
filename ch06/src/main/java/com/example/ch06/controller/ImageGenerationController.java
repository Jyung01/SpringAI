package com.example.ch06.controller;

import com.example.ch06.service.ImageGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class ImageGenerationController {

    private final ImageGenerationService service;

    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/ai/image-generation")
    public String imageGeneration() {
        return "/image-generation";
    }

    @ResponseBody
    @PostMapping(
            value = "/ai/image-generate",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public Mono<String> generate(@RequestParam String description) {
        return service.generate(description);
    }
}
