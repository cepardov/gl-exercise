package com.globallogic.exercise.service;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;
import com.globallogic.exercise.exception.DateSelectedException;
import com.globallogic.exercise.exception.MagnitudeSelectedException;
import com.globallogic.exercise.exception.PlaceException;

import java.util.List;

public interface QueryEarthquakeService {

    ResponseDTO queryBetweenDates(BetweenDatesDTO betweenDatesDTO) throws DateSelectedException;
    ResponseDTO queryBetweenMagnitudes(BetweenMagnitudesDTO betweenMagnitudesDTO) throws MagnitudeSelectedException;

    ResponseDTO queryBetweenOneOrMoreDate(List<BetweenDatesDTO> betweenDatesDTOList);
    ResponseDTO queryAllQuakesByPlace(String place) throws PlaceException;
}
