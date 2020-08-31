package com.globallogic.exercise.controller

import com.globallogic.exercise.dto.BetweenDatesDTO
import com.globallogic.exercise.dto.BetweenMagnitudesDTO
import com.globallogic.exercise.dto.ResponseDTO
import com.globallogic.exercise.service.QueryEarthquakeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import java.time.LocalDate

class EarthquakeControllerSpec extends Specification {

    EarthquakeController earthquakeController
    QueryEarthquakeService earthquakeService = Stub(QueryEarthquakeService)

    def setup() {
        earthquakeController = new EarthquakeController(earthquakeService)
    }

    def "Consulta sismos entre 2 fechas exito"() {
        given:
        ResponseDTO responseDTO = Mock(ResponseDTO)
        BetweenDatesDTO betweenDatesDTO = new BetweenDatesDTO()
        betweenDatesDTO.startTime = LocalDate.parse("2020-08-01")
        betweenDatesDTO.endTime = LocalDate.parse("2020-08-02")
        earthquakeService.queryBetweenDates(_ as BetweenDatesDTO) >> responseDTO
        when:
        def res = earthquakeController.queryBetweenDates(betweenDatesDTO)
        then:
        res != null
        res instanceof ResponseEntity<ResponseDTO>
        res.statusCode instanceof HttpStatus
    }

    def "Consulta sismos entre 2 magnitudes"() {
        given:
        ResponseDTO responseDTO = Mock(ResponseDTO)
        BetweenMagnitudesDTO betweenMagnitudesDTO = new BetweenMagnitudesDTO()
        betweenMagnitudesDTO.minMagnitude = 1.0
        betweenMagnitudesDTO.maxMagnitude = 2.0
        earthquakeService.queryBetweenMagnitudes(betweenMagnitudesDTO) >> responseDTO
        when:
        def res = earthquakeController.queryBetweenMagnitude(betweenMagnitudesDTO)
        then:
        res != null
        res instanceof ResponseEntity<ResponseDTO>
        res.statusCode == HttpStatus.OK
    }
}
