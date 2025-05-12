package com.spring.ai.springaidemo.ChatClient.advisors;

import lombok.NonNull;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import reactor.core.publisher.Flux;

import java.util.Objects;

@Slf4j
public class LogAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {
    private AdvisedRequest before(AdvisedRequest request) {
        log.info("在AdvisedRequest中->{}", request.toString());
        return request;
    }

    private AdvisedResponse after(AdvisedResponse advisedResponse) {
        log.info("在advisedRespone中->{}", advisedResponse.toString());
        return advisedResponse;
    }

    private Flux<AdvisedResponse> after(Flux<AdvisedResponse> advisedResponseFlux) {
        return advisedResponseFlux.map(r -> {
            log.info("在异步advisedResponseFlux中->{}", r.toString());
            return r;
        });
    }

    @Override
    @NonNull
    public AdvisedResponse aroundCall(@NonNull AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        AdvisedRequest before = before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(before);
        return Objects.requireNonNull(after(advisedResponse));
    }

    @Override
    @NonNull
    public Flux<AdvisedResponse> aroundStream(@NonNull AdvisedRequest advisedRequest, @NonNull StreamAroundAdvisorChain chain) {
        AdvisedRequest before = before(advisedRequest);

        Flux<AdvisedResponse> advisedResponseFlux = chain.nextAroundStream(before);

        return after(advisedResponseFlux);
    }

    @Override
    public String getName() {
        return LogAdvisor.class.getName();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
