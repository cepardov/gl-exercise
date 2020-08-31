package com.globallogic.exercise.service;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;
import com.globallogic.exercise.exception.DateSelectedException;
import com.globallogic.exercise.exception.MagnitudeSelectedException;

public interface QueryEarthquakeService {

    ResponseDTO queryBetweenDates(BetweenDatesDTO betweenDatesDTO) throws DateSelectedException;
    ResponseDTO queryBetweenMagnitudes(BetweenMagnitudesDTO betweenMagnitudesDTO) throws MagnitudeSelectedException;
}
