package com.idle.couponservice.infrastruture.stream.in;


import com.idle.couponservice.infrastruture.CreatedMissionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatedMissionEventService {
    private final CreatedMissionJpaRepository createdMissionJpaRepository;

    @Transactional
    public void updateCreatedMissionCompleteState(Long createdMissionId) {
        createdMissionJpaRepository.updateCompletedStatus(createdMissionId);
    }


}
