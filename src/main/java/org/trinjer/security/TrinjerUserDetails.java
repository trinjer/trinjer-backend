package org.trinjer.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.trinjer.domain.UserEntity;

import java.util.Collection;
import java.util.Collections;

public class TrinjerUserDetails extends UserEntity implements UserDetails {

    public static TrinjerUserDetails of(UserEntity userEntity) {
        TrinjerUserDetails trinjerUserDetails = new TrinjerUserDetails();
        trinjerUserDetails.setId(userEntity.getId());
        trinjerUserDetails.setUsername(userEntity.getUsername());
        trinjerUserDetails.setPassword(userEntity.getPassword());
        trinjerUserDetails.setEmail(userEntity.getEmail());
        return trinjerUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
