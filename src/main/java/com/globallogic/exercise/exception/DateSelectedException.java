package com.globallogic.exercise.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateSelectedException extends Exception {

    final String message;
}
