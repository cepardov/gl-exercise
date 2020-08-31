package com.globallogic.exercise.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BetweenDatesDTO {

    private LocalDate startTime;
    private LocalDate endTime;
}
