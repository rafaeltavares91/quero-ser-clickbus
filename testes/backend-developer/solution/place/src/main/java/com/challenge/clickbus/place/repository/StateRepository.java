package com.challenge.clickbus.place.repository;

import com.challenge.clickbus.place.domain.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
}