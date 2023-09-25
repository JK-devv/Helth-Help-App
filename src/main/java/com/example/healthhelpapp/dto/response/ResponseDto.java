package com.example.healthhelpapp.dto.response;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResponseDto extends RepresentationModel<ResponseDto> {
    private String message;
}
