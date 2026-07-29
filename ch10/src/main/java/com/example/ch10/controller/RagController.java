package com.example.ch10.controller;

import com.example.ch10.service.RagService;
import com.example.ch10.service.TxtPdfWordEtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Log4j2
public class RagController {

    private final RagService service;

    @GetMapping("/ai/rag")
    public String rag() {
        return "/rag";
    }

    @ResponseBody
    @GetMapping("/ai/rag-clear")
    public String ragClear() {

        service.clearVectorStore();
        return "벡터 저장소 데이터를 비웠습니다";
    }

    @ResponseBody
    @PostMapping("/ai/rag-etl")
    public String ragEtl(@RequestParam("attach") MultipartFile attach,
                                @RequestParam("source") String source,
                                @RequestParam("chunkSize") int chunkSize,
                                @RequestParam("minChunkSizeChars") int minChunkSizeChars) throws IOException {

        service.ragEtl(attach, source, chunkSize, minChunkSizeChars);

        return "ETL 작업을 완료 했습니다";

    }

    @ResponseBody
    @PostMapping("/ai/rag-chat")
    public String ragChat(@RequestParam("question") String question,
                                @RequestParam("score") Double score,
                                @RequestParam("source") String source) throws IOException {

        String result = service.ragChat(question, score, source);

        return result;

    }

}
