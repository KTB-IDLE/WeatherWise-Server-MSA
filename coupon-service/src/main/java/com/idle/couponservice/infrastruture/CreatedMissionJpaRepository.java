package com.idle.couponservice.infrastruture;

import com.idle.couponservice.domain.createdMission.CreatedMission;
import com.idle.couponservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface CreatedMissionJpaRepository extends JpaRepository<CreatedMission, Long> {


    @Modifying
    @Query("UPDATE CreatedMission cm SET cm.isCompleted = true WHERE cm.id = :createdMissionId")
    void updateCompletedStatus(Long createdMissionId);

    @Query("SELECT CASE WHEN COUNT(cm) > 0 THEN TRUE ELSE FALSE END " +
            "FROM CreatedMission AS cm " +
            "WHERE cm.challenger.userId = :userId " +
            "AND cm.isCompleted = TRUE " +
            "AND cm.createdAt >= :#{#date.toLocalDate().atStartOfDay()} " +
            "AND cm.createdAt < :#{#date.toLocalDate().plusDays(1).atStartOfDay()}")
    boolean hasCompletedMissionToday(@Param("userId") Long userId, @Param("date") LocalDateTime date);
}
