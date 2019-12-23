package com.challenge.clickbus.place.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.challenge.clickbus.place.dto.CityDTO;
import com.challenge.clickbus.place.dto.CreatePlaceDTO;
import com.challenge.clickbus.place.dto.PlaceDTO;
import com.challenge.clickbus.place.dto.StateDTO;
import com.challenge.clickbus.place.exception.ResourceNotFoundException;
import com.challenge.clickbus.place.service.PlaceService;
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

    private CreatePlaceDTO createPlaceDTOWithValidFields = CreatePlaceDTO.builder()
            .name("Faria Lima")
            .cityId(1l)
            .build();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(placeController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void createPlaceBadRequest() throws Exception {
        CreatePlaceDTO placeDTOWithoutRequiredFields = CreatePlaceDTO.builder().build();

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(placeDTOWithoutRequiredFields)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createPlaceStatusNotFound() throws Exception {
        when(placeController.createPayment(any())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createPlaceDTOWithValidFields)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", equalTo("Resource Not Found")));
    }

    @Test
    public void createPlace() throws Exception {
        PlaceDTO placeDTO = PlaceDTO.builder()
                .id(1l)
                .name("Faria Lima")
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

        when(placeService.create(any())).thenReturn(placeDTO);

        mockMvc.perform(post(PlaceController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createPlaceDTOWithValidFields)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Faria Lima")));
    }

}
