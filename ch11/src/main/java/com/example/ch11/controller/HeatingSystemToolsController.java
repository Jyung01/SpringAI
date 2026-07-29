package com.example.ch11.controller;

import com.example.ch11.service.DateTimeToolsService;
import com.example.ch11.service.HeatingSystemToolsService;
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
public class HeatingSystemToolsController {

    private final HeatingSystemToolsService service;

    @GetMapping("/ai/heating-system-tools")
    public String dateTimeTools() {
        return "/heating-system-tools";
    }



    @ResponseBody
    @PostMapping("/ai/heating-system-tools")
    public String chat(@RequestParam("question") String question) {
        String answer = service.chat(question);
        return answer;
    }

}
