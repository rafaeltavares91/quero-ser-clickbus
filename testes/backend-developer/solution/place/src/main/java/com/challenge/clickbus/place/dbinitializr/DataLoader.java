package com.challenge.clickbus.place.dbinitializr;

import com.challenge.clickbus.place.domain.City;
import com.challenge.clickbus.place.domain.State;
import com.challenge.clickbus.place.repo.CityRepository;
import com.challenge.clickbus.place.repo.StateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public DataLoader(StateRepository stateRepository, CityRepository cityRepository) {
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

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
