package com.challenge.clickbus.place.repository;

import com.challenge.clickbus.place.domain.Place;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends CrudRepository<Place, Long> {
}