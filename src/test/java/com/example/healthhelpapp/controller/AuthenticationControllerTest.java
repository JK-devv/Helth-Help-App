package com.example.healthhelpapp.controller;

import com.example.healthhelpapp.dto.Role;
import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.security.JwtAuthenticatedFilter;
import com.example.healthhelpapp.security.JwtService;
import com.example.healthhelpapp.security.SecurityConfig;
import com.example.healthhelpapp.service.AuthenticationService;
import com.example.healthhelpapp.service.impl.LinkHandlerServiceImpl;
import com.example.healthhelpapp.service.impl.UserService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.healthhelpapp.TestConstant.TEST_NAME;
import static com.example.healthhelpapp.TestConstant.TEST_TOKEN;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Import({LinkHandlerServiceImpl.class, SecurityConfig.class,
        JwtAuthenticatedFilter.class, JwtService.class})
@WebMvcTest(value = AuthenticationController.class)
class AuthenticationControllerTest {
    @MockBean
    private AuthenticationService authenticationService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private LinkHandlerServiceImpl linkHandlerService;

    @Test
    @SneakyThrows
    void authentication() {
        String request = "{\"id\":0,\"name\":\"test name\",\"password\":\"1111\",\"role\":\"DOCTOR\",\"enabled\":true," +
                "\"username\":\"test name\",\"credentialsNonExpired\":true," +
                "\"accountNonLocked\":true,\"accountNonExpired\":true}";
        UserEntity requestDto = UserEntity.builder()
                .password("1111")
                .role(Role.DOCTOR)
                .name(TEST_NAME)
                .build();
        String response = "{\"token\":\"token\",\"_links\":{\"start_page\":{\"href\":\"http://localhost/start\"}}}";
        when(authenticationService.authenticate(requestDto))
                .thenReturn(TEST_TOKEN);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(content().string(response));
    }
}