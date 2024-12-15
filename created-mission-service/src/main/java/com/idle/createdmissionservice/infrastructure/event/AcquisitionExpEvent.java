package com.idle.createdmissionservice.infrastructure.event;

import lombok.*;
import org.springframework.stereotype.Service;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AcquisitionExpEvent {
    private int exp;
    private Long userId;

    public static AcquisitionExpEvent createEvent(int exp , Long userId) {
        return AcquisitionExpEvent.builder()
                .exp(exp)
                .userId(userId)
                .build();
    }
}
