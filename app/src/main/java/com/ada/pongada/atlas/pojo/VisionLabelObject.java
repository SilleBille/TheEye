package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionLabelObject {

    @SerializedName("mid")
    private String mid;
    @SerializedName("description")
    private String description;
    @SerializedName("score")
    private Double score;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

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

}
