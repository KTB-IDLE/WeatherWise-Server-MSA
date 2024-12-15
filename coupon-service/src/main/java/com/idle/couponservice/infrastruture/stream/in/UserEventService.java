package com.idle.couponservice.infrastruture.stream.in;

import com.idle.couponservice.domain.user.User;
import com.idle.couponservice.infrastruture.UserJpaRepository;
import com.idle.couponservice.infrastruture.event.CreateUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserEventService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void save(CreateUserEvent event) {
        userJpaRepository.save(User.signUpSocialLogin(event.getSerialId() , event.getPassword() ,
                event.getProvider() , event.getRole() , event.getNickname()));
    }
}
