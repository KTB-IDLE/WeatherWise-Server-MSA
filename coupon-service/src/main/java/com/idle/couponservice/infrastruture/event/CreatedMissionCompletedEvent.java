package com.idle.couponservice.infrastruture.event;

import lombok.*;

@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class CreatedMissionCompletedEvent {
    private Long createdMissionId;
    private boolean isCompleted;
}
