package com.globallogic.exercise.controller;

import com.globallogic.exercise.dto.BetweenDatesDTO;
import com.globallogic.exercise.dto.BetweenMagnitudesDTO;
import com.globallogic.exercise.dto.ResponseDTO;
import com.globallogic.exercise.exception.DateSelectedException;
import com.globallogic.exercise.exception.MagnitudeSelectedException;
import com.globallogic.exercise.exception.PlaceException;
import com.globallogic.exercise.service.QueryEarthquakeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/earthquake")
public class EarthquakeController {

    private final QueryEarthquakeService queryEarthquakeService;

    public EarthquakeController(QueryEarthquakeService queryEarthquakeService) {
        this.queryEarthquakeService = queryEarthquakeService;
    }

    @PostMapping("/between-dates")
    public ResponseEntity<ResponseDTO> queryBetweenDates(@RequestBody BetweenDatesDTO betweenDatesDTO) throws DateSelectedException {
        ResponseDTO responseDTO = queryEarthquakeService.queryBetweenDates(betweenDatesDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/between-magnitude")
    public ResponseEntity<ResponseDTO> queryBetweenMagnitude(@RequestBody BetweenMagnitudesDTO betweenMagnitudesDTO) throws MagnitudeSelectedException {
        ResponseDTO responseDTO = queryEarthquakeService.queryBetweenMagnitudes(betweenMagnitudesDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/between-one-or-more-dates")
    public ResponseEntity<ResponseDTO> queryOneOrMoreDates(@RequestBody List<BetweenDatesDTO> betweenDatesDTOList) {
        ResponseDTO responseDTO = queryEarthquakeService.queryBetweenOneOrMoreDate(betweenDatesDTOList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllByPlace/{place}")
    public ResponseEntity<ResponseDTO> queryAllQuakesByPlace(@PathVariable("place") String place) throws PlaceException {
        ResponseDTO responseDTO = queryEarthquakeService.queryAllQuakesByPlace(place);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/getQuakesByPlaceAndDates/{place}")
    public ResponseEntity<ResponseDTO> countEarthquakesByPlaceAndDates(@PathVariable("place") String place, @RequestBody BetweenDatesDTO betweenDatesDTO) throws DateSelectedException, PlaceException {
        ResponseDTO responseDTO = queryEarthquakeService.countEarthquakesByPlaceAndDates(place, betweenDatesDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
