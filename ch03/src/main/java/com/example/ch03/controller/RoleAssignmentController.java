package com.example.ch03.controller;

import com.example.ch03.service.RoleAssignmentService;
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
public class RoleAssignmentController {

    private final RoleAssignmentService service;

    @GetMapping("/ai/role-assignment")
    public String page() {
        return "/role-assignment";
    }

    @ResponseBody
    @PostMapping(value = "/ai/role-assignment",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<String> prompt(@RequestParam String requirements) {
        return service.prompt(requirements);
    }
}
