package com.example.ch03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** 예제 목록이 있는 첫 화면을 연결한다. */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }
}
