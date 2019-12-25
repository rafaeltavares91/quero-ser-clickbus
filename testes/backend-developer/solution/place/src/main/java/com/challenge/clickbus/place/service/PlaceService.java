package com.challenge.clickbus.place.service;

import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.repository.CityRepository;
import com.challenge.clickbus.place.repository.PlaceRepository;
import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import static com.challenge.clickbus.place.util.ModelMapperHelper.MODELMAPPER_HELPER;

@Service
@AllArgsConstructor
public class PlaceService {

    private final Slugify slugify = new Slugify();

    private final PlaceRepository placeRepository;
    private final CityRepository cityRepository;

    public PlaceDTO create(CreateUpdatePlaceDTO createUpdatePlaceDTO) {
        ModelMapper mapper = MODELMAPPER_HELPER.getInstance();

        Place place = mapper.map(createUpdatePlaceDTO, Place.class);
        place.setSlug(slugify.slugify(place.getName()));
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
        place.setSlug(slugify.slugify(place.getName()));
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

    public List<PlaceDTO> findByName(Optional<String> name) {
        ModelMapper mapper = MODELMAPPER_HELPER.getInstance();
        Type targetType = new TypeToken<List<PlaceDTO>>(){}.getType();
        if(name.isPresent()) {
            return mapper.map(placeRepository.findByName(name.get()), targetType);
        }
        return mapper.map(placeRepository.findAll(), targetType);
    }
}