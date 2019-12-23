package com.challenge.clickbus.place.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public enum ModelMapperHelper {

    MODELMAPPER_HELPER;

    private ModelMapper modelMapper;

    ModelMapperHelper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper getInstance() {
        return modelMapper;
    }

}
