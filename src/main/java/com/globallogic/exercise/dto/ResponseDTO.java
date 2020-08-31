
package com.globallogic.exercise.dto;

import com.globallogic.exercise.vo.Feature;
import com.globallogic.exercise.vo.Metadata;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseDTO {

    private List<Double> bbox = new ArrayList<>();
    private List<Feature> features = new ArrayList<>();
    private Metadata metadata;
    private String type;

}
