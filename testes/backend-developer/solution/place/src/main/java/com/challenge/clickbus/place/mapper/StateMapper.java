package com.challenge.clickbus.place.mapper;

import com.challenge.clickbus.place.domain.State;
import com.challenge.clickbus.place.dto.StateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StateMapper {

    StateMapper INSTANCE = Mappers.getMapper(StateMapper.class);

    StateDTO stateToStateDTO(State state);

    State stateDTOToState(StateDTO stateDTO);

}
