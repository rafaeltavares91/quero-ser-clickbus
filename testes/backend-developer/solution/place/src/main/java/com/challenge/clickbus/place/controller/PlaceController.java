package com.challenge.clickbus.place.controller;

import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.service.PlaceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@RestController
@RequestMapping(PlaceController.BASE_URL)
public class PlaceController {

    public static final String BASE_URL = "/places";

    private final PlaceService placeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaceDTO createPlace(@Valid @RequestBody CreateUpdatePlaceDTO placeDTO) {
        return placeService.create(placeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaceDTO updatePlace(@PathVariable Long id, @Valid @RequestBody CreateUpdatePlaceDTO placeDTO) {
        return placeService.updatePlace(id, placeDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PlaceDTO getPlaceById(@PathVariable("id") Long id) {
        return placeService.findById(id);
    }

    @GetMapping({"/list","list/{name}"})
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceDTO> getPlaces(@PathVariable("name") Optional<String> name) {
        return placeService.findByName(name);
    }

}
