package com.example.ch06.controller;

import com.example.ch06.service.ImageAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ImageAnalysisController {

    private final ImageAnalysisService service;

    @GetMapping("/ai/image-analysis")
    public String imageAnalysis() {
        return "/image-analysis";
    }

    @GetMapping("/ai/video-analysis")
    public String videoAnalysis() {
        return "/video-analysis";
    }

    @ResponseBody
    @PostMapping(
            value = "/ai/image-analysis",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE
    )
    public Flux<String> imageAnalysis(
            @RequestParam String question,
            @RequestParam("attach") MultipartFile attach) throws IOException {

        // 이미지가 없거나 이미지 형식이 아니면 모델을 호출하지 않는다.
        if (attach.isEmpty() || attach.getContentType() == null
                || !attach.getContentType().startsWith("image/")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "이미지 파일을 선택해 주세요."
            );
        }

        String actualQuestion = question == null || question.isBlank()
                ? "이 이미지에 무엇이 있는지 한국어로 설명해 주세요."
                : question.trim();

        return service.analysis(
                actualQuestion,
                attach.getContentType(),
                attach.getBytes()
        );
    }
}
