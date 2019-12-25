package com.challenge.clickbus.place.service;

import com.challenge.clickbus.place.domain.City;
import com.challenge.clickbus.place.domain.Place;
import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.repository.CityRepository;
import com.challenge.clickbus.place.repository.PlaceRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class PlaceServiceTest {

    private PlaceService placeService;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private CityRepository cityRepository;

    private City city;
    private Place place;

    @Before
    public void setUp() {
        city = City.builder()
                .id(1L)
                .name("São Paulo")
                .build();

        place = Place.builder()
                .id(1L)
                .name("Faria Lima")
                .slug("faria-lima")
                .city(city)
                .build();

        MockitoAnnotations.initMocks(this);

        placeService = new PlaceService(placeRepository, cityRepository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void createPlaceThrowsResourceNotFoundException()  {
        when(cityRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.create(CreateUpdatePlaceDTO.builder().name("Faria Lima").cityId(1L).build());
    }

    @Test
    public void createPlace() {
        when(placeRepository.save(any())).thenReturn(place);
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));

        PlaceDTO returnedPlace = placeService.create(CreateUpdatePlaceDTO.builder().name("Faria Lima").cityId(1L).build());

        assertEquals(1L, returnedPlace.getId().longValue());
        assertEquals("Faria Lima", returnedPlace.getName());
        assertEquals("faria-lima", returnedPlace.getSlug());
        assertEquals("São Paulo", returnedPlace.getCity().getName());
        verify(cityRepository, times(1)).findById(anyLong());
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updatePlaceThrowsResourceNotFoundException()  {
        when(placeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.updatePlace(1L, CreateUpdatePlaceDTO.builder().name("Faria Lima").cityId(1L).build());
    }

    @Test
    public void updatePlace() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));
        when(cityRepository.findById(anyLong())).thenReturn(Optional.of(city));
        when(placeRepository.save(any())).thenReturn(place);

        PlaceDTO returnedPlace = placeService.updatePlace(1L, CreateUpdatePlaceDTO.builder().name("Berrini").cityId(1L).build());

        assertEquals(1L, returnedPlace.getId().longValue());
        assertEquals("Berrini", returnedPlace.getName());
        assertEquals("berrini", returnedPlace.getSlug());
        assertEquals("São Paulo", returnedPlace.getCity().getName());
        verify(placeRepository, times(1)).findById(anyLong());
        verify(cityRepository, times(1)).findById(anyLong());
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findByIdThrowsResourceNotFoundException() {
        when(placeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        placeService.findById(1L);
    }

    @Test
    public void findById() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.of(place));

        PlaceDTO returnedPlace = placeService.findById(1L);

        assertEquals(1L, returnedPlace.getId().longValue());
        assertEquals("Faria Lima", returnedPlace.getName());
        assertEquals("faria-lima", returnedPlace.getSlug());
        assertEquals("São Paulo", returnedPlace.getCity().getName());
        verify(placeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findByName() {
        when(placeRepository.findAll()).thenReturn(Lists.newArrayList(place));

        List<PlaceDTO> places = placeService.findByName(Optional.ofNullable(null));
        PlaceDTO firstPlace = places.get(0);

        assertEquals(places.size(), 1);
        assertEquals(1L, firstPlace.getId().longValue());
        assertEquals("Faria Lima", firstPlace.getName());
        verify(placeRepository, times(1)).findAll();
        verify(placeRepository, times(0)).findByName(anyString());
    }

    @Test
    public void findByNameWithValue() {
        when(placeRepository.findByName(anyString())).thenReturn(Lists.newArrayList(place));

        List<PlaceDTO> places = placeService.findByName(Optional.ofNullable("Faria Lima"));
        PlaceDTO firstPlace = places.get(0);

        assertEquals(places.size(), 1);
        assertEquals(1L, firstPlace.getId().longValue());
        assertEquals("Faria Lima", firstPlace.getName());
        verify(placeRepository, times(0)).findAll();
        verify(placeRepository, times(1)).findByName(any());
    }

}
