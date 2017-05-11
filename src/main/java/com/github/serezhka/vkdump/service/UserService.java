package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.UserRepository;
import com.github.serezhka.vkdump.dao.entity.UserEntity;
import com.vk.api.sdk.client.actors.UserActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserActor tokenOwner;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserActor tokenOwner) {
        this.userRepository = userRepository;
        this.tokenOwner = tokenOwner;
    }

    public UserEntity getTokenOwner() {
        return userRepository.findByUserId(tokenOwner.getId());
    }

    public UserEntity findById(int userId) {
        return userRepository.findByUserId(userId);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void save(UserEntity user) {
        if (userRepository.findByUserId(user.getUserId()) == null) {
            userRepository.save(user);
        }
    }
}
