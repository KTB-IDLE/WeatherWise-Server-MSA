package com.idle.createdmissionservice.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter
public class CreatedMissionCompletedEvent {
    private Long createdMissionId;
    private boolean isCompleted;

    public static CreatedMissionCompletedEvent createEvent(Long createdMissionId) {
        return CreatedMissionCompletedEvent.builder()
                .createdMissionId(createdMissionId)
                .isCompleted(true)
                .build();
    }
}
