package com.example.ch03.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class MultiMessagesService {

    private final ChatClient chatClient;

    public MultiMessagesService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    /**
     * 세션에 보관한 이전 User/Assistant 메시지와 새 질문을 함께 전송한다.
     * 따라서 첫 요청에서 정한 이름 같은 문맥을 다음 요청에서도 기억할 수 있다.
     */
    public Flux<String> prompt(String question, List<Message> history) {
        UserMessage newUserMessage = new UserMessage(question);
        List<Message> messages;

        // 실제 요청 중에는 목록이 바뀌지 않도록 복사본을 만들어 사용한다.
        synchronized (history) {
            messages = new ArrayList<>(history);
            messages.add(newUserMessage);
        }

        StringBuilder fullAnswer = new StringBuilder();
        return chatClient.prompt()
                .messages(messages)
                .stream()
                .content()
                .doOnNext(fullAnswer::append)
                .doOnComplete(() -> {
                    // 스트리밍이 정상 종료된 뒤에만 이번 대화를 다음 요청의 문맥에 추가한다.
                    synchronized (history) {
                        history.add(newUserMessage);
                        history.add(new AssistantMessage(fullAnswer.toString()));
                    }
                });
    }
}
