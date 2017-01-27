package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.UserRepository;
import com.github.serezhka.vkdump.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
