package com.globallogic.exercise.service;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;

public interface QueryEarthquakeService {

    ResponseDTO queryBetweenDates(BetweenDatesDTO betweenDatesDTO);
    //ResponseDTO queryBetweenMagnitudes(BetweenMagnitudesDTO betweenMagnitudesDTO);
}
