package com.challenge.clickbus.place.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlaceDTO {

    @NotNull
    private String name;

    private String slug;

    @NotNull
    private Long cityId;

}
