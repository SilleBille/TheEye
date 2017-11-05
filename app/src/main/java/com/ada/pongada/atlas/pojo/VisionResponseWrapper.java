package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionResponseWrapper {

    @SerializedName("responses")
    @Expose
    private List<VisionResponse> responses;

    public List<VisionResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<VisionResponse> responses) {
        this.responses = responses;
    }

}
