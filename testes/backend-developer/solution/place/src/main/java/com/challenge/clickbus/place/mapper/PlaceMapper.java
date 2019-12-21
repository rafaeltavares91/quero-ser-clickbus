package com.challenge.clickbus.place.mapper;

import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.PlaceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlaceMapper {

    PlaceMapper INSTANCE = Mappers.getMapper(PlaceMapper.class);

    PlaceDTO placeToPlaceDTO(Place place);

    Place placeDTOToPlace(PlaceDTO placeDTO);

}
