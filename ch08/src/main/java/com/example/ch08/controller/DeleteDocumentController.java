package com.example.ch08.controller;

import com.example.ch08.service.DeleteDocumentService;
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
public class DeleteDocumentController {

    private final DeleteDocumentService service;

    @GetMapping("/ai/delete-document")
    public String deleteDocument() {
        return "/delete-document";
    }

    @ResponseBody
    @PostMapping("/ai/delete-document")
    public String deleteDocument(@RequestParam("question") String question) {
        // 화면의 질문 값은 예제 설명용이며, Service에 정의된 메타데이터 조건으로 삭제한다.
        service.deleteDocument();

        return "헌법 문서 중 연도가 1987 이상인 문서를 삭제했습니다.";
    }
}
