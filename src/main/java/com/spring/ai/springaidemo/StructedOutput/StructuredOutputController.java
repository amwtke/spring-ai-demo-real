package com.spring.ai.springaidemo.StructedOutput;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ai.springaidemo.StructedOutput.models.ActorFiles;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StructuredOutputController {

    private final ChatClient openAiChatClient;

    StructuredOutputController(ChatClient openAiChatClient) {
        this.openAiChatClient = openAiChatClient;
    }

    @GetMapping(value = "/ai/cc/actor_files", produces = "text/json;charset=utf-8")
    public String genStructuredOutput(@RequestParam(value = "msg", defaultValue = "tom hanks") String msg) {
        ActorFiles actor = openAiChatClient.prompt()
                .user(u -> u.text("Generate the filmography of 5 movies for {actor}.").param("actor", msg))
                .call()
                .entity(ActorFiles.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(actor);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
