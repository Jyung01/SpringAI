package com.example.ch06.service;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class ImageGenerationService {

    private final ImageModel imageModel;

    public ImageGenerationService(ImageModel imageModel) {
        // spring.ai.model.image=openai 설정으로 OpenAI ImageModel이 주입된다.
        this.imageModel = imageModel;
    }

    public Mono<String> generate(String description) {
        if (description == null || description.isBlank()) {
            return Mono.error(
                    new IllegalArgumentException("이미지 설명을 입력해 주세요.")
            );
        }

        // OpenAI 호출은 동기 방식이므로 별도 작업 스레드에서 실행한다.
        return Mono.fromCallable(() -> {
                    ImageResponse response = imageModel.call(
                            new ImagePrompt(description.trim())
                    );

                    String base64Image = response.getResult()
                            .getOutput()
                            .getB64Json();

                    if (base64Image == null || base64Image.isBlank()) {
                        throw new IllegalStateException(
                                "OpenAI가 이미지 데이터를 반환하지 않았습니다."
                        );
                    }

                    return base64Image;
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
