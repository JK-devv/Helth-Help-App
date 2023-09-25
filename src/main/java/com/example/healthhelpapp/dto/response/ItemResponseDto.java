package com.example.healthhelpapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponseDto extends RepresentationModel<ItemResponseDto> {
    private int id;
    private String name;
    private int count;
    private String categoryName;
}
