package com.globallogic.exercise.service

import com.globallogic.exercise.dto.BetweenDatesDTO
import com.globallogic.exercise.dto.BetweenMagnitudesDTO
import com.globallogic.exercise.dto.ResponseDTO
import com.globallogic.exercise.exception.DateSelectedException
import com.globallogic.exercise.exception.MagnitudeSelectedException
import com.globallogic.exercise.exception.PlaceException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

import java.time.LocalDate

class QueryEarthquakeServiceSpec extends Specification {

    RestTemplate restTemplate
    QueryEarthquakeServiceImpl queryEarthquakeService

    def setup() {
        restTemplate = new RestTemplate()
        queryEarthquakeService = new QueryEarthquakeServiceImpl(restTemplate)
    }

    def "Consulta sismos entre 2 fechas queryBetweenDates"() {
        given:
        BetweenDatesDTO betweenDatesDTO = new BetweenDatesDTO()
        betweenDatesDTO.startTime = LocalDate.parse("2020-08-01")
        betweenDatesDTO.endTime = LocalDate.parse("2020-08-02")
        when:
        ResponseDTO responseDTO = queryEarthquakeService.queryBetweenDates(betweenDatesDTO)

        then:
        responseDTO != null
    }

    def "Consulta sismos entre 2 fechas erroneas"() {
        given:
        BetweenDatesDTO betweenDatesDTO = new BetweenDatesDTO()
        betweenDatesDTO.startTime = LocalDate.parse("2020-08-02")
        betweenDatesDTO.endTime = LocalDate.parse("2020-08-01")

        when:
        queryEarthquakeService.queryBetweenDates(betweenDatesDTO)

        then:
         def res = thrown(DateSelectedException)
        res.message == "Las fecha final no puede ser anterior a la de inicio"
    }

    def "Consulta sismos entre 2 magnitudes correcto"() {
        given:
        BetweenMagnitudesDTO betweenMagnitudesDTO = new BetweenMagnitudesDTO()
        betweenMagnitudesDTO.minMagnitude = 1.0
        betweenMagnitudesDTO.maxMagnitude = 2.0
        when:
        ResponseDTO res = queryEarthquakeService.queryBetweenMagnitudes(betweenMagnitudesDTO)
        then:
        res != null
    }

    def "Consulta sismos entre 2 magnitudes bad request"() {
        given:
        BetweenMagnitudesDTO betweenMagnitudesDTO = new BetweenMagnitudesDTO()
        betweenMagnitudesDTO.minMagnitude = 2.0
        betweenMagnitudesDTO.maxMagnitude = 1.0
        when:
        queryEarthquakeService.queryBetweenMagnitudes(betweenMagnitudesDTO)
        then:
        def res = thrown(MagnitudeSelectedException)
        res.message == "La magnitud màxima no puede ser menor a la magnitud mínima"
    }

    def "Consulta sismos entre 1 o mas fechas"() {
        given:
        BetweenDatesDTO betweenDatesDTO = new BetweenDatesDTO()
        List<BetweenDatesDTO> betweenDatesDTOList = new ArrayList<>()
        betweenDatesDTO.startTime = LocalDate.parse("2019-10-01")
        betweenDatesDTO.endTime = LocalDate.parse("2019-10-03")
        betweenDatesDTOList.add(betweenDatesDTO)
        betweenDatesDTO.startTime = LocalDate.parse("2019-10-01")
        betweenDatesDTO.endTime = LocalDate.parse("2019-10-03")
        betweenDatesDTOList.add(betweenDatesDTO)
        when:
        ResponseDTO responseDTO = queryEarthquakeService.queryBetweenOneOrMoreDate(betweenDatesDTOList)
        then:
        responseDTO != null
    }

    def "Consulta sismos por pais" () {
        given:
        def place = "Chile"
        when:
        ResponseDTO responseDTO = queryEarthquakeService.queryAllQuakesByPlace(place)
        then:
        responseDTO != null
    }

    def "Consulta sismos por pais bad request" () {
        given:
        def place = ""
        when:
        queryEarthquakeService.queryAllQuakesByPlace(place)
        then:
        def res = thrown(PlaceException)
        res.message == "Lugar/País no debe estar vacío"
    }

}
