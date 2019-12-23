package com.challenge.clickbus.place.service;

import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.CreatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.repository.CityRepository;
import com.challenge.clickbus.place.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;

    public PlaceDTO create(CreatePlaceDTO createPlaceDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Place place = mapper.map(createPlaceDTO, Place.class);

        place.setCity(cityRepository
                .findById(createPlaceDTO.getCityId())
                .orElseThrow(ResourceNotFoundException::new));

        return mapper.map(placeRepository.save(place), PlaceDTO.class);
    }

}