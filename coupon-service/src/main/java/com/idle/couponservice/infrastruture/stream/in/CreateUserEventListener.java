package com.idle.couponservice.infrastruture.stream.in;

import com.idle.couponservice.infrastruture.event.CreateUserEvent;
import com.idle.couponservice.infrastruture.event.CreatedMissionCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CreateUserEventListener {
    private final UserEventService userEventService;

    @Bean
    public Consumer<CreateUserEvent> createdUser() {
        return message -> {
            log.info("메시지 소비");
            userEventService.save(message);
        };
    }
}
