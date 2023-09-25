package com.example.healthhelpapp.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto extends RepresentationModel<TokenResponseDto> {
    private String token;
}
