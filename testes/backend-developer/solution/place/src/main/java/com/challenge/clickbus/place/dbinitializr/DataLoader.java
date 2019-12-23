package com.challenge.clickbus.place.dbinitializr;

import com.challenge.clickbus.place.domain.City;
import com.challenge.clickbus.place.domain.State;
import com.challenge.clickbus.place.repository.CityRepository;
import com.challenge.clickbus.place.repository.StateRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    @Override
    public void run(String... args) {
        loadData();
    }

    private void loadData() {
        State sp = stateRepository.save(State.builder().name("São Paulo").abbreviation("SP").build());
        State rn = stateRepository.save(State.builder().name("Rio Grande do Norte").abbreviation("RN").build());

        cityRepository.save(City.builder().name("São Paulo").state(sp).build());
        cityRepository.save(City.builder().name("Natal").state(rn).build());
    }
}
