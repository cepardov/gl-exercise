
package com.globallogic.exercise.vo;

import lombok.Data;

import java.util.List;

@Data
public class GeoJson {

    private List<Double> bbox;
    private List<Feature> features;
    private Metadata metadata;
    private String type;

}
