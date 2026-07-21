package com.example.ch04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** 예제 목록 화면을 루트 주소에 연결한다. */
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "/index";
    }
}
