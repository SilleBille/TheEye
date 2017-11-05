package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionRequestWrapper {

    @SerializedName("requests")
    private List<VisionRequest> requests;

    public List<VisionRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<VisionRequest> requests) {
        this.requests = requests;
    }

}
