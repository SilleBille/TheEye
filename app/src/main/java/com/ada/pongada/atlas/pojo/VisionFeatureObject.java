package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionFeatureObject {

    @SerializedName("type")
    private String type;
    @SerializedName("maxResults")
    private Integer maxResult;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(Integer maxResult) {
        this.maxResult = maxResult;
    }

}
