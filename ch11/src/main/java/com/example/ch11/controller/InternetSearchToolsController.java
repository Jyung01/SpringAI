package com.example.ch11.controller;

import com.example.ch11.service.InternetSearchToolsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Controller
public class InternetSearchToolsController {

    private final InternetSearchToolsService service;

    @GetMapping("/ai/internet-search-tools")
    public String internetSearchTools() {
        return "/internet-search-tools";
    }

    @ResponseBody
    @PostMapping("/ai/internet-search-tools")
    public String internetSearchTools(@RequestParam("question") String question) {
        String answer = service.chat(question);
        return answer;
    }

}
