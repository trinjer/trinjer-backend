package org.trinjer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.trinjer.domain.UserEntity;
import org.trinjer.domain.repositories.UserRepository;
import org.trinjer.exceptions.UserExistException;

import java.util.Collection;

/**
 * Created by arturjoshi on 06-Jul-17.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Collection<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity registerNewUser(UserEntity userEntity) throws UserExistException {
        String email = userEntity.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserExistException(email);
        }

        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }
}
