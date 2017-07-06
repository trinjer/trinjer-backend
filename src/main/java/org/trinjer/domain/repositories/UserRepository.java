package org.trinjer.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trinjer.domain.UserEntity;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}
