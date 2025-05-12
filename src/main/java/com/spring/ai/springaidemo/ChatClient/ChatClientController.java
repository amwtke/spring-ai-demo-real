package com.spring.ai.springaidemo.ChatClient;

import com.spring.ai.springaidemo.ChatClient.advisors.LogAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@RestController
@Slf4j
public class ChatClientController {
    private final ChatClient chatClient;

    @Autowired
    private ChatClient.Builder openAiChatClientBuilder;

    @Autowired
    private MessageChatMemoryAdvisor messageChatMemoryAdvisor;

    @Autowired
    private LogAdvisor logAdvisor;

    ChatClientController(ChatClient openAiChatClient) {
        this.chatClient = openAiChatClient;
    }

    @RequestMapping(value = "/ai/cc/stream", produces = "text/html;charset=utf-8")
    public Flux<String> chatResponseStream(@RequestParam(value = "msg", defaultValue = "你好吗？") String msg) {
        return chatClient.prompt(msg)
                .advisors()
                .stream()
                .content();
    }

    @RequestMapping(value = "/ai/cc", produces = "text/html;charset=utf-8")
    public String chatResponse(@RequestParam(value = "msg", defaultValue = "你好吗？") String msg) {
        return chatClient.prompt(msg)
                .call()
                .content();
    }

    @RequestMapping(value = "/ai/memory/cc", produces = "text/html;charset=utf-8")
    public String chatResponse2(@RequestParam(value = "msg", defaultValue = "你好吗？") String msg,
                                @RequestParam(value = "key", defaultValue = "default") String key) {
        return this.openAiChatClientBuilder.build().prompt(msg)
                .advisors(messageChatMemoryAdvisor)
                .advisors(logAdvisor)
                .advisors(a -> a.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 4))
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, key))
                .call()
                .content();
    }

    @RequestMapping(value = "/ai/memory/cc/stream", produces = "text/html;charset=utf-8")
    public Flux<String> chatResponseStreamWithMemo(@RequestParam(value = "msg", defaultValue = "你好吗？") String msg,
                                                   @RequestParam(value = "key", defaultValue = "default") String key) {
        return this.openAiChatClientBuilder.build().prompt(msg)
                .advisors(messageChatMemoryAdvisor)
                .advisors(logAdvisor)
                .advisors(a -> a.param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 4))
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, key))
                .stream()
                .content();
    }
}
