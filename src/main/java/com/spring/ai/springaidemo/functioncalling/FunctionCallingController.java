package com.spring.ai.springaidemo.functioncalling;

import com.spring.ai.springaidemo.ChatClient.advisors.LogAdvisor;
import com.spring.ai.springaidemo.functioncalling.Tools.AdvisorLogSwitchTool;
import com.spring.ai.springaidemo.functioncalling.Tools.DateTimeTool;
import com.spring.ai.springaidemo.functioncalling.Tools.WeatherLocationForDaysTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
public class FunctionCallingController {
    private final ChatClient.Builder openAiChatClientBuilder;
    private final LogAdvisor logAdvisor;

    FunctionCallingController(ChatClient.Builder builder, LogAdvisor logAdvisor) {
        this.logAdvisor = logAdvisor;
        this.openAiChatClientBuilder = builder;
    }

    @RequestMapping(value = "/ai/tool", produces = "text/html;charset=utf-8")
    public String testToolCalling(@RequestParam(value = "msg", defaultValue = "现在几点了？") String msg) {
        log.info("msg = {}", msg);
        return openAiChatClientBuilder.build()
                .prompt(msg)
                .tools(new DateTimeTool(), new WeatherLocationForDaysTool(), new AdvisorLogSwitchTool(logAdvisor))
                .advisors(logAdvisor)
                .call()
                .content();
    }
}
