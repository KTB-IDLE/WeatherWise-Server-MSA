package com.idle.createdmissionservice.infrastructure.event;

import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
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
