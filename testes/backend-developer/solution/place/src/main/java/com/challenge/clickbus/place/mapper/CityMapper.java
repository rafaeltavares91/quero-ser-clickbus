package com.challenge.clickbus.place.mapper;

import com.challenge.clickbus.place.domain.City;
import com.challenge.clickbus.place.dto.CityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    CityDTO cityToCityDTO(City city);

    public City cityDTOToCity(CityDTO cityDTO);

}
