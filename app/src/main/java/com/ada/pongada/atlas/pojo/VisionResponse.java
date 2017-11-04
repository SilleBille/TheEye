package com.ada.pongada.atlas.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class VisionResponse {

    @SerializedName("logoAnnotations")
    private List<VisionLogoObject> logoAnnotations;
    @SerializedName("labelAnnotations")
    private List<VisionLabelObject> labelAnnotations;

    public List<VisionLogoObject> getLogoAnnotations() {
        return logoAnnotations;
    }

    public void setLogoAnnotations(List<VisionLogoObject> logoAnnotations) {
        this.logoAnnotations = logoAnnotations;
    }

    public List<VisionLabelObject> getLabelAnnotations() {
        return labelAnnotations;
    }

    public void setLabelAnnotations(List<VisionLabelObject> labelAnnotations) {
        this.labelAnnotations = labelAnnotations;
    }

}
