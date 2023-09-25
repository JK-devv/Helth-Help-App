package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Role;
import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.example.healthhelpapp.TestConstant.TEST_NAME;
import static com.example.healthhelpapp.TestConstant.TEST_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {UserService.class})
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldLoadUserByUsername() {
        UserEntity userEntity = UserEntity.builder()
                .name(TEST_NAME)
                .password(TEST_PASSWORD)
                .role(Role.MANAGER)
                .build();

        when(userRepository.findByName(TEST_NAME))
                .thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername(TEST_NAME);

        assertEquals(userEntity.getName(), userDetails.getUsername());

        verify(userRepository, times(1)).findByName(TEST_NAME);
    }

    @Test
    void shouldNotLoadUserByNotExistedUsername() {

        when(userRepository.findByName(TEST_NAME))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                userService.loadUserByUsername(TEST_NAME),
                String.format("User with name: %s does nit exist", TEST_NAME));

        verify(userRepository, times(1)).findByName(TEST_NAME);
    }
}