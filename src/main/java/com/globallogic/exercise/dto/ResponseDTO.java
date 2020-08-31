
package com.globallogic.exercise.dto;

import com.globallogic.exercise.vo.Feature;
import com.globallogic.exercise.vo.Metadata;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private List<Double> bbox;
    private List<Feature> features;
    private Metadata metadata;
    private String type;

}
