package com.idle.couponservice.infrastruture.stream.in;

import com.idle.couponservice.infrastruture.CreatedMissionJpaRepository;
import com.idle.couponservice.infrastruture.UserJpaRepository;
import com.idle.couponservice.infrastruture.event.CreatedMissionCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CreatedMissionCompletedEventListener {
    private final CreatedMissionEventService createdMissionEventService;

    @Bean
    public Consumer<CreatedMissionCompletedEvent> createdMissionUpdate() {
        return message -> {
            log.info("메시지 소비");
            createdMissionEventService.updateCreatedMissionCompleteState(message.getCreatedMissionId());
        };
    }

}
