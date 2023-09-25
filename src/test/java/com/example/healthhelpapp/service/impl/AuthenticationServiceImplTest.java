package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Role;
import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static com.example.healthhelpapp.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {AuthenticationServiceImpl.class})
class AuthenticationServiceImplTest {
    @Autowired
    private AuthenticationServiceImpl authenticationService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;


    @Test
    void shouldAuthenticate() {
        UserEntity testUser = UserEntity.builder()
                .id(1)
                .name(TEST_NAME)
                .role(Role.MANAGER)
                .password(TEST_PASSWORD)
                .build();

        when(userService.loadUserByUsername(testUser.getUsername())).thenReturn(testUser);
        when(jwtService.generateToken(testUser)).thenReturn(TEST_TOKEN);

        String actual = authenticationService.authenticate(testUser);
        assertEquals(TEST_TOKEN, actual);

        verify(userService, times(1))
                .loadUserByUsername(testUser.getUsername());
        verify(jwtService, times(1))
                .generateToken(testUser);
    }

    @Test
    void shouldNotAuthenticate() {
        UserEntity testUser = UserEntity.builder()
                .name(TEST_NAME)
                .build();

        when(userService.loadUserByUsername(testUser.getUsername()))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () ->
                authenticationService.authenticate(testUser),
                String.format("User with name: %s does nit exist", testUser.getName()));

        verify(userService, times(1))
                .loadUserByUsername(testUser.getUsername());
    }
}