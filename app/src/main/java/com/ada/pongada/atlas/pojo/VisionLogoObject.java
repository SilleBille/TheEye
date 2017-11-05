package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionLogoObject {

    @SerializedName("description")
    private String description;
    @SerializedName("score")
    private Double score;
    @SerializedName("boundingPoly")
    private VisionBoundingPolyObject boundingPoly;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public VisionBoundingPolyObject getBoundingPoly() {
        return boundingPoly;
    }

    public void setBoundingPoly(VisionBoundingPolyObject boundingPoly) {
        this.boundingPoly = boundingPoly;
    }

}
