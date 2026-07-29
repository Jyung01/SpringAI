package com.example.ch10.controller;

import com.example.ch10.service.CompressionQueryTransformerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Log4j2
@RequiredArgsConstructor
@Controller
public class RewriteQueryTransformerController {

    private final CompressionQueryTransformerService service;


    @GetMapping("/ai/rewrite-query-transformer")
    public String rewriteQueryTransformer() {
        return "/rewrite-query-transformer";
    }

    @ResponseBody
    @PostMapping("/ai/rewrite-query-transformer")
    public String rewriteQueryTransformer(@RequestParam("question") String question,
                                              @RequestParam("score") double score,
                                              @RequestParam("source") String source,
                                              HttpSession session) {

        //String answer = service.chatWithCompression(question, score, source, session.getId());

        return "";
    }
}
