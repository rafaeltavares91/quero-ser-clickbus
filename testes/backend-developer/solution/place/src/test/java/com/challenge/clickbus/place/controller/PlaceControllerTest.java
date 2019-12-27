package com.challenge.clickbus.place.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.clickbus.place.dto.CityDTO;
import com.challenge.clickbus.place.dto.CreateUpdatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.dto.StateDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.service.PlaceService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PlaceControllerTest extends AbstractRestControllerTest {

    @InjectMocks
    private PlaceController placeController;

    @Mock
    private PlaceService placeService;

    private MockMvc mockMvc;

    private PlaceDTO placeDTO;
    private CreateUpdatePlaceDTO createUpdatePlaceDTO;

    @Before
    public void setUp() {
        placeDTO = PlaceDTO.builder()
                .id(1l)
                .name("Faria Lima")
                .slug("faria-lima")
                .city(CityDTO.builder()
                        .id(1l)
                        .name("São Paulo")
                        .state(StateDTO.builder()
                                .id(1l)
                                .name("São Paulo")
                                .abbreviation("SP")
                                .build())
                        .build())
                .build();

        createUpdatePlaceDTO = CreateUpdatePlaceDTO.builder()
                .name("Faria Lima")
                .cityId(1l)
                .build();

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(placeController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void createPlaceBadRequest() throws Exception {
        CreateUpdatePlaceDTO placeDTOWithoutRequiredFields = CreateUpdatePlaceDTO.builder().build();

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(placeDTOWithoutRequiredFields)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPlaceNotFound() throws Exception {
        when(placeService.create(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUpdatePlaceDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo("Resource Not Found")));
    }

    @Test
    public void createPlace() throws Exception {
        when(placeService.create(any())).thenReturn(placeDTO);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUpdatePlaceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Faria Lima")))
                .andExpect(jsonPath("$.slug", equalTo("faria-lima")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")));
    }

    @Test
    public void updatePlaceBadRequest() throws Exception {
        CreateUpdatePlaceDTO placeDTOWithoutRequiredFields = CreateUpdatePlaceDTO.builder().build();

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(placeDTOWithoutRequiredFields)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updatePlaceNotFound() throws Exception {
        when(placeService.updatePlace(anyLong(), any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUpdatePlaceDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo("Resource Not Found")));
    }

    @Test
    public void updatePlace() throws Exception {
        when(placeService.updatePlace(anyLong(), any())).thenReturn(placeDTO);

        mockMvc.perform(put(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createUpdatePlaceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Faria Lima")))
                .andExpect(jsonPath("$.slug", equalTo("faria-lima")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")));
    }

    @Test
    public void getPlaceByIdNotFound() throws Exception {
        when(placeService.findById(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo("Resource Not Found")));
    }

    @Test
    public void getPlaceById() throws Exception {
        when(placeService.findById(any())).thenReturn(placeDTO);

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Faria Lima")))
                .andExpect(jsonPath("$.slug", equalTo("faria-lima")))
                .andExpect(jsonPath("$.city.id", equalTo(1)))
                .andExpect(jsonPath("$.city.name", equalTo("São Paulo")))
                .andExpect(jsonPath("$.city.state.id", equalTo(1)))
                .andExpect(jsonPath("$.city.state.abbreviation", equalTo("SP")));
    }

    @Test
    public void getPlaces() throws Exception {
        when(placeService.findByName(any())).thenReturn(Lists.newArrayList(placeDTO));

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/list"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("Faria Lima")))
                .andExpect(jsonPath("$.[0].slug", equalTo("faria-lima")))
                .andExpect(jsonPath("$.[0].city.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.name", equalTo("São Paulo")));
    }

    @Test
    public void getPlacesWithName() throws Exception {
        when(placeService.findByName(any())).thenReturn(Lists.newArrayList(placeDTO));

        mockMvc.perform(get(PlaceController.BASE_URL.concat("/list/Faria Lima"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].name", equalTo("Faria Lima")))
                .andExpect(jsonPath("$.[0].slug", equalTo("faria-lima")))
                .andExpect(jsonPath("$.[0].city.id", equalTo(1)))
                .andExpect(jsonPath("$.[0].city.name", equalTo("São Paulo")));
    }

}
