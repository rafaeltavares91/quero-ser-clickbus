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
public class CreateUpdatePlaceDTO {

    @NotNull
    private String name;

    @NotNull
    private Long cityId;

}
