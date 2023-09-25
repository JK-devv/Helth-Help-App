package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Role;
import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByName(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                String.format("User with name: %s does nit exist", username)));
        return new User(userEntity.getName(), userEntity.getPassword(),
                mapRoleToAuthority(userEntity.getRole()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthority(Role usersRole) {
        return List.of(new SimpleGrantedAuthority(usersRole.name()));
    }
}
