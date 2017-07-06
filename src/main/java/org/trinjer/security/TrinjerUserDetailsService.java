package org.trinjer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.trinjer.domain.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TrinjerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) {
        return TrinjerUserDetails.of(Optional.ofNullable(userRepository.findByUsername(s))
                .orElseThrow(() -> new UsernameNotFoundException("No such user")));
    }
}
