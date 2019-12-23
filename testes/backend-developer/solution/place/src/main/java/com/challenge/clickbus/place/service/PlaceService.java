package com.challenge.clickbus.place.service;

import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
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

    public PlaceDTO create(CreateUpdatePlaceDTO ceateUpdatePlaceDTO) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Place place = mapper.map(ceateUpdatePlaceDTO, Place.class);

        place.setCity(cityRepository
                .findById(ceateUpdatePlaceDTO.getCityId())
                .orElseThrow(ResourceNotFoundException::new));

        return mapper.map(placeRepository.save(place), PlaceDTO.class);
    }

    public PlaceDTO updatePlace(Long id, CreateUpdatePlaceDTO ceateUpdatePlaceDTO) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        place.setName(ceateUpdatePlaceDTO.getName());
        place.setSlug(ceateUpdatePlaceDTO.getSlug());
        place.setCity(cityRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(placeRepository.save(place), PlaceDTO.class);
    }

    public PlaceDTO findById(Long id) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper.map(placeRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new),
                PlaceDTO.class);
    }
}