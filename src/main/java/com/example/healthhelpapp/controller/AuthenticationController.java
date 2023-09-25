package com.example.healthhelpapp.controller;

import com.example.healthhelpapp.dto.UserEntity;
import com.example.healthhelpapp.dto.response.TokenResponseDto;
import com.example.healthhelpapp.service.AuthenticationService;
import com.example.healthhelpapp.service.LinkHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final LinkHandlerService linkHandlerService;

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public TokenResponseDto authentication(@RequestBody UserEntity user) {
        return TokenResponseDto.builder()
                .token(authenticationService.authenticate(user))
                .build()
                .add(linkHandlerService.addRelLinkToStartPage());
    }
}
