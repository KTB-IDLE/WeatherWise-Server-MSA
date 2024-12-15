package com.idle.createdmissionservice.infrastructure.stream.out;

import com.idle.createdmissionservice.infrastructure.event.AcquisitionExpEvent;
import com.idle.createdmissionservice.infrastructure.event.CreatedMissionCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class AcquisitionExpEventPublisher {

    private final Sinks.Many<AcquisitionExpEvent> sink = Sinks.many().unicast().onBackpressureBuffer();


    // Issued Sink
    @Bean
    public Supplier<Flux<AcquisitionExpEvent>> acquisitionExp() {
        return () -> sink.asFlux()
                .doOnError(error -> {
                    // 에러 처리 로직
                    System.err.println("Error in couponEventSupplier: " + error.getMessage());
                });
    }

    public void publish(AcquisitionExpEvent event) {
        log.info("이벤트 발행");
        sink.tryEmitNext(event);
    }
}
