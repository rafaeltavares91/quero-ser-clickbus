package com.challenge.clickbus.place.service;

import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.repository.CityRepository;
import com.challenge.clickbus.place.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.challenge.clickbus.place.util.ModelMapperHelper.MODELMAPPER_HELPER;

@Service
@AllArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;

    public PlaceDTO create(CreateUpdatePlaceDTO createUpdatePlaceDTO) {
        ModelMapper mapper = MODELMAPPER_HELPER.getInstance();

        Place place = mapper.map(createUpdatePlaceDTO, Place.class);
        place.setCity(cityRepository
                .findById(createUpdatePlaceDTO.getCityId())
                .orElseThrow(ResourceNotFoundException::new));

        return mapper.map(placeRepository.save(place), PlaceDTO.class);
    }

    public PlaceDTO updatePlace(Long id, CreateUpdatePlaceDTO createUpdatePlaceDTO) {
        Place place = placeRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        place.setName(createUpdatePlaceDTO.getName());
        place.setCity(cityRepository
                .findById(id)
                .orElseThrow(ResourceNotFoundException::new));

        return MODELMAPPER_HELPER.getInstance().map(
                placeRepository.save(place),
                PlaceDTO.class);
    }

    public PlaceDTO findById(Long id) {
        return MODELMAPPER_HELPER.getInstance().map(
                placeRepository
                        .findById(id)
                        .orElseThrow(ResourceNotFoundException::new),
                PlaceDTO.class);
    }

}