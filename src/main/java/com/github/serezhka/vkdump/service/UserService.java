package com.github.serezhka.vkdump.service;

import com.github.serezhka.vkdump.dao.UserRepository;
import com.github.serezhka.vkdump.dao.entity.UserEntity;
import com.github.serezhka.vkdump.dto.UserDTO;
import com.github.serezhka.vkdump.util.converter.EntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(EntityConverter::userEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDTO findById(long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        if (user != null) {
            return EntityConverter.userEntityToDto(user);
        }
        return null;
    }

    public void save(UserDTO user) {
        if (userRepository.findByUserId(user.getId()) == null) {
            userRepository.save(EntityConverter.userDtoToEntity(user));
        }
    }
}
