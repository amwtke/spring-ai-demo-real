package config;

import com.spring.ai.springaidemo.ChatClient.advisors.LogAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ChatClientConfig {
    @Bean
    ChatClient.Builder openAiChatClientBuilder(OpenAiChatModel openAiChatModel) {
        return ChatClient.builder(openAiChatModel);
    }

    @Bean
    ChatClient openAiChatClient(ChatClient.Builder openAiChatClientBuilder) {
        return openAiChatClientBuilder.build();
    }

    @Bean
    ChatMemory openAiChatMemory() {
        return new InMemoryChatMemory();
    }

    @Bean
    MessageChatMemoryAdvisor openAiChatMessageChatMemoryAdvisor(ChatMemory openAiChatMemory) {
        return new MessageChatMemoryAdvisor(openAiChatMemory);
    }

    @Bean
    LogAdvisor openAiChatLogAdvisor() {
        return new LogAdvisor();
    }

}
