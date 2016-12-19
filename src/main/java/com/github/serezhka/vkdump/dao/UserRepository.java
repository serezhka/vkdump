package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserId(Long userId);
}
