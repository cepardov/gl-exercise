package com.globallogic.exercise.service;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;
import com.globallogic.exercise.exception.DateSelectedException;
import com.globallogic.exercise.exception.MagnitudeSelectedException;
import com.globallogic.exercise.exception.PlaceException;
import com.globallogic.exercise.vo.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class QueryEarthquakeServiceImpl implements QueryEarthquakeService {

    private final RestTemplate restTemplate;

    @Autowired
    public QueryEarthquakeServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseDTO queryBetweenDates(BetweenDatesDTO betweenDatesDTO) throws DateSelectedException {
        if (betweenDatesDTO.getStartTime() == null) throw new DateSelectedException("La fecha de inicio no debe estar vacío");
        if (betweenDatesDTO.getEndTime() == null) throw new DateSelectedException("La fecha final no debe estar vacío");
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

    @Override
    public ResponseDTO queryBetweenOneOrMoreDate(List<BetweenDatesDTO> betweenDatesDTOList) {
        ResponseDTO responseDTOFinal = new ResponseDTO();
        betweenDatesDTOList.forEach(betweenDatesDTO -> {
            ResponseDTO responseDTO = restTemplate.getForObject(
                    "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime={startTime}&endtime={endTime}",
                    ResponseDTO.class,
                    betweenDatesDTO.getStartTime(),
                    betweenDatesDTO.getEndTime());
            List<Double> bbox = Stream.concat(responseDTOFinal.getBbox().stream(), responseDTO.getBbox().stream()).collect(Collectors.toList());
            List<Feature> features = Stream.concat(responseDTOFinal.getFeatures().stream(), responseDTO.getFeatures().stream()).collect(Collectors.toList());
            responseDTOFinal.setBbox(bbox);
            responseDTOFinal.setFeatures(features);
            responseDTOFinal.setMetadata(responseDTO.getMetadata());
            responseDTOFinal.setType(responseDTO.getType());
        });
        return responseDTOFinal;
    }

    @Override
    public ResponseDTO queryAllQuakesByPlace(String place) throws PlaceException {
        if (place == null || place.isEmpty()) throw new PlaceException("Lugar/País no debe estar vacío");
        ResponseDTO responseDTO = restTemplate.getForObject("https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson", ResponseDTO.class);
        List<Feature> featureList = responseDTO.getFeatures().stream().filter(feature -> feature.getProperties().getPlace().toLowerCase().contains(place.toLowerCase()))
                .collect(Collectors.toList());
        responseDTO.setFeatures(featureList);
        return responseDTO;
    }

    @Override
    public ResponseDTO countEarthquakesByPlaceAndDates(String place, BetweenDatesDTO betweenDatesDTO) throws DateSelectedException, PlaceException {
        if (place == null || place.isEmpty()) throw new PlaceException("Lugar/País no debe estar vacío");
        if (betweenDatesDTO.getStartTime() == null) throw new DateSelectedException("La fecha de inicio no debe estar vacío");
        if (betweenDatesDTO.getEndTime() == null) throw new DateSelectedException("La fecha final no debe estar vacío");
        if (!betweenDatesDTO.getStartTime().isBefore(betweenDatesDTO.getEndTime()))
            throw new DateSelectedException("Las fecha final no puede ser anterior a la de inicio");
        ResponseDTO responseDTO = restTemplate.getForObject(
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime={startTime}&endtime={endTime}",
                ResponseDTO.class,
                betweenDatesDTO.getStartTime(),
                betweenDatesDTO.getEndTime());
        List<Feature> featureList = responseDTO.getFeatures().stream().filter(feature -> feature.getProperties().getPlace().toLowerCase().contains(place.toLowerCase()))
                .collect(Collectors.toList());
        responseDTO.setFeatures(featureList);
        return responseDTO;
    }
}
