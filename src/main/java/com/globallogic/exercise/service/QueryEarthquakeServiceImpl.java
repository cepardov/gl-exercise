package com.globallogic.exercise.service;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;
import com.globallogic.exercise.exception.DateSelectedException;
import com.globallogic.exercise.exception.MagnitudeSelectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QueryEarthquakeServiceImpl implements QueryEarthquakeService {

    private final RestTemplate restTemplate;

    @Autowired
    public QueryEarthquakeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseDTO queryBetweenDates(BetweenDatesDTO betweenDatesDTO) throws DateSelectedException {
        if (!betweenDatesDTO.getStartTime().isBefore(betweenDatesDTO.getEndTime()))
            throw new DateSelectedException("Las fecha final no puede ser anterior a la de inicio");
        return restTemplate.getForObject(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime={startTime}&endtime={endTime}",
                ResponseDTO.class,
                betweenDatesDTO.getStartTime(),
                betweenDatesDTO.getEndTime());
    }

    @Override
    public ResponseDTO queryBetweenMagnitudes(BetweenMagnitudesDTO betweenMagnitudesDTO) throws MagnitudeSelectedException {
        if (betweenMagnitudesDTO.getMinMagnitude() > betweenMagnitudesDTO.getMaxMagnitude())
            throw new MagnitudeSelectedException("La magnitud màxima no puede ser menor a la magnitud mínima");
        return restTemplate.getForObject(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude={minmagnitude}&maxmagnitude={maxmagnitude}",
                ResponseDTO.class,
                betweenMagnitudesDTO.getMinMagnitude(),
                betweenMagnitudesDTO.getMaxMagnitude()
        );
    }
}
