package com.example.webflux.config;

import com.example.webflux.componts.GreetingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author zetu
 * @date 2022/8/5
 */
@Configuration
public class GreetingRouter {

    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                greetingHandler::hello);
    }

    @Bean
    public RouterFunction<ServerResponse> topicRoute(GreetingHandler greetingHandler) {
        return RouterFunctions.route(RequestPredicates.PUT("/updateTopic")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                greetingHandler::updateTopic);
    }
}
