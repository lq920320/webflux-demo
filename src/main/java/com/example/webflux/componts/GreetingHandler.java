package com.example.webflux.componts;

import com.example.webflux.eventdemo.Topic;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


/**
 * @author zetu
 * @date 2022/8/5
 */
@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, Spring WebFlux!"));
    }

    public Mono<ServerResponse> updateTopic(ServerRequest request) {
        return request.bodyToMono(Topic.class)
                // need return Mono<ServerResponse>，topicMapper.updateTopic(topic)
                .flatMap(topic -> null)
                .flatMap(topic -> ok().bodyValue(topic))
                .onErrorResume(e -> badRequest().build());

    }
}
