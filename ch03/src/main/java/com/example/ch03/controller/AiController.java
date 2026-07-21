package com.example.ch03.controller;

import com.example.ch03.service.AiService;
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
public class AiController {

    private final AiService aiService;

    @GetMapping("/ai/prompt-template")
    public String promptTemplate() {

        return "/prompt-template";
    }

    @PostMapping(
            value = "/ai/prompt-template",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, // 클라이언트가 보내는 데이터 타입, 기본 폼 전송, 일반적으로 생략
            produces = MediaType.APPLICATION_NDJSON_VALUE           // NDJSON(NewLine Delimited JSON), JSON 객체를 행단위로 전송
    )
    @ResponseBody // Flux<String>을 뷰 이름이 아니라 HTTP 응답 본문으로 전송한다.
    public Flux<String> promptTemplate(@RequestParam("statement") String statement,
                               @RequestParam("language") String language) {

        log.info("statement : {}", statement);
        log.info("language : {}", language);

        Flux<String> fluxString = aiService.promptTemplate1(statement, language);

        return fluxString;

    }
}
