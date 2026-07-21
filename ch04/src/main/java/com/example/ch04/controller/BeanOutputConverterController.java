package com.example.ch04.controller;

import com.example.ch04.dto.HotelInfo;
import com.example.ch04.service.BeanOutputConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BeanOutputConverterController {

    private final BeanOutputConverterService service;

    @GetMapping("/ai/bean-output-converter")
    public String beanOutputConverter() {
        return "/bean-output-converter";
    }

    @ResponseBody
    @PostMapping("/ai/bean-output-converter")
    public HotelInfo beanOutputConverter(@RequestParam String city) {
        return service.convert(city);
    }
}
