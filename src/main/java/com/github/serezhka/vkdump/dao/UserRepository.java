package com.github.serezhka.vkdump.dao;

import com.github.serezhka.vkdump.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(Integer userId);
}
