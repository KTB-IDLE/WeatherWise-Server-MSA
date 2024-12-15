package com.idle.authservice.infrastructure.stream.out;


import com.idle.authservice.infrastructure.event.CreateUserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
public class CreatedUserEventPublisher {
    // 비동기적으로 이벤트를 발행
    private final Sinks.Many<CreateUserEvent> sink = Sinks.many().unicast().onBackpressureBuffer();


    // Issued Sink
    @Bean
    public Supplier<Flux<CreateUserEvent>> createUser() {
        return () -> sink.asFlux()
                .doOnError(error -> {
                    // 에러 처리 로직
                    System.err.println("Error in couponEventSupplier: " + error.getMessage());
                });
    }

    public void publish(CreateUserEvent event) {
        sink.tryEmitNext(event);
    }
}
